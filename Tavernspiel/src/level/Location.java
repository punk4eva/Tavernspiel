
package level;

import animation.Animation;
import animation.assets.GrassAnimation;
import blob.ParticleAnimation;
import creatureLogic.CreatureDistribution;
import gui.mainToolbox.MouseInterpreter;
import items.builders.ItemBuilder;
import items.equipment.HeldWeapon;
import items.equipment.weapons.*;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import javax.swing.ImageIcon;
import logic.ConstantFields;
import logic.Distribution;
import logic.GameSettings;
import logic.ImageHandler;
import logic.Utils;
import tiles.Tile;
import tiles.assets.Well;

/**
 *
 * @author Adam Whittaker
 * 
 * This class stores all information for generating a Stage.
 */
public abstract class Location{
    
    public final String name;
    public final ImageIcon tileset;
    public final ImageIcon waterImage;
    protected RoomDistribution roomDistrib;
    protected CreatureDistribution[] spawnDistribution;
    public LevelFeeling feeling = LevelFeeling.STANDARD;
    public final HashMap<String, ImageIcon> tilemap = new HashMap<>();
    public int depth = 1;
    public final Region region;
    public final Function<List<Room>, RoomStructure> structure;

    public final ImageIcon backgroundImage;
    
    public GrassAnimation lowGrass;
    public GrassAnimation highGrass;
    
    private final HashMap<String, String> descriptionMap = new HashMap<>();
    
    public enum Region{
        KIRI(0, new Distribution(new int[]{})),
        KYOU(1, new Distribution(new int[]{})),
        SUDA(2, new Distribution(new int[]{10, 10, 12, 8, 6, 1, 2, 2})),
        HURI(3, new Distribution(new int[]{8, 9, 10, 11, 6, 2, 1, 1}));
        
        final int code;
        final Distribution weaponRarities;
        private Region(int c, Distribution wr){
            code = c;
            weaponRarities = wr;
        }
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param bgiPath The path to the background image.
     * @param water The water gen. chance.
     * @param r The Region.
     * @param struct The room generation algorithm.
     */
    public Location(String n, String tiles, String bgiPath, String water, Region r, Function<List<Room>, RoomStructure> struct){
        name = n;
        waterImage = new ImageIcon("graphics/tilesets/"+water+".png");
        tileset = new ImageIcon("graphics/tilesets/"+tiles+".png");
        region = r;
        structure = struct;
        backgroundImage = Utils.getBGI("graphics/background/"+bgiPath+".png");
    }
    
    
    
    /**
     * Retrieves a random WeaponEntry based on their respective rarities.
     * @return
     */
    public HeldWeapon getRandomWeapon(){
        switch(region){
            case KIRI: switch((int)region.weaponRarities.next()){
                case 0:
                case 1:
                case 2:
                case 3: 
                case 4:
                case 5:
                case 6:
                default:
            }
            case KYOU: switch((int)region.weaponRarities.next()){
                case 0:
                case 1:
                case 2:
                case 3: 
                case 4:
                case 5:
                case 6:
                default:
            }
            case SUDA: switch((int)region.weaponRarities.next()){
                case 0: return new Nunchaku();
                case 1: return new Kama();
                case 2: return new Tanto();
                case 3: return new Sai();
                case 4: return new Kanabo();
                case 5: return new Katana();
                case 6: return new Naginata();
                default: return new Kusarigama();
            }
            case HURI: switch((int)region.weaponRarities.next()){
                case 0: return new Catnails();
                case 1: return new CounterHammer();
                case 2: return new Ulysses();
                case 3: return new Saraga();
                case 4: return new Triaxe();
                case 5: return new CandleStaff();
                case 6: return new GreatestSword();
                default: return new FateScythe();
            }
            default: throw new IllegalStateException("Invalid region");
        }
    }
    
    /**
     * Retrieves the Image for the Tile with the given name.
     * @param str
     * @return
     */
    public ImageIcon getImage(String str){
        return tilemap.get(str);
    }
    
    /**
     * Retrieves the Chest Icon for this Region.
     * @param loc The name of the Location
     * @return
     */
    public static ImageIcon getChestIcon(String loc){
        return ItemBuilder.getIcon(LOCATION_MAP.get(loc).region.code*16, 16);
    }
    
    /**
     * Retrieves the Locked Chest Icon for this Region.
     * @param loc The name of the Location
     * @return
     */
    public static ImageIcon getLockedChestIcon(String loc){
        return ItemBuilder.getIcon(LOCATION_MAP.get(loc).region.code*16+64, 16);
    }
    
    /**
     * Retrieves the Skeletal Remains Icon for this Region.
     * @param loc The name of the Location
     * @return
     */
    public static ImageIcon getSkeletalRemainsIcon(String loc){
        return ItemBuilder.getIcon(LOCATION_MAP.get(loc).region.code*16+64, 0);
    }
    
    /**
     * Retrieves the Locked Crystal Icon for this Region.
     * @param loc The name of the Location
     * @return
     */
    public static ImageIcon getCrystalChestIcon(String loc){
        return ItemBuilder.getIcon(LOCATION_MAP.get(loc).region.code*16, 0);
    }
    
    /**
     * Gets the Animation for the decorated wall if there is any.
     * @param x The x coordinate of the wall.
     * @param y The y coordinate of the wall.
     * @return
     */
    public abstract Animation getWallAnimation(int x, int y);
    
    /**
     * Returns the bare-bones description of the given Tile from the description
     * map.
     * @param str The Tile's name.
     * @return
     */
    public String getBaseDescription(String str){
        if(str.endsWith("well")) return Well.getDescription(str.substring(0, str.length()-4));
        return descriptionMap.get(str);
    }
    
    
    
    public static final Location SHKODER_LOCATION = 
            new Location("Shkoder", "shkoderTileset", "shkoderBackground", "shkoderWater", Region.HURI, (list) -> new RoomStructure.SpiderCorridor(new Dimension(80, 80), Location.SHKODER_LOCATION, list, 15, true)){
        @Override
        public Animation getWallAnimation(int x, int y){
            return new ParticleAnimation.NullAnimation();
        }
    };
    static{
        ImageHandler.initializeIcons(SHKODER_LOCATION);
        SHKODER_LOCATION.lowGrass = new GrassAnimation(new int[][]{
                    {3,2}, {15,4}, {0,8}, {13,10}, {2,11}, {8,12}, {11,15}
        }, "lowgrass", SHKODER_LOCATION, 57, 177, 249, 
                40, 100, 190, 210, 190, 254);
        SHKODER_LOCATION.highGrass = new GrassAnimation(new int[][]{
                    {0,8}, {1,1}, {3,4}, {3,9}, {4,0}, {4,14}, {5,5}, {8,2}, {8,8}, 
            {9,0}, {9,12}, {10,4}, {11,8}, {12,6}, {12,14}, {14,9}, {14,12},
            {14,14}, {14,1}, {15,4}
        }, "highgrass", SHKODER_LOCATION, 57, 177, 249, 
                40, 100, 190, 210, 190, 254);
        SHKODER_LOCATION.roomDistrib = new RoomDistribution(SHKODER_LOCATION, 3, 12);
        SHKODER_LOCATION.descriptionMap.put("void", "This is your typical bottomless pit.");
        SHKODER_LOCATION.descriptionMap.put("chasm","It would probably hurt if you fell down there.");
        SHKODER_LOCATION.descriptionMap.put("floor","This is the floor.");
        SHKODER_LOCATION.descriptionMap.put("lowgrass","Civilization in the caves used to make bioluminescent grass rare, but now it has begun reclaiming the land.");
        SHKODER_LOCATION.descriptionMap.put("highgrass","The grass is so high you can't see past!");
        SHKODER_LOCATION.descriptionMap.put("emptywell","Unfortunately, there is no more water in this well.");
        SHKODER_LOCATION.descriptionMap.put("wall","This is the wall of the cave.");
        SHKODER_LOCATION.descriptionMap.put("closeddoor","This is a door.");
        SHKODER_LOCATION.descriptionMap.put("door","This is a door.");
        SHKODER_LOCATION.descriptionMap.put("opendoor","This is an open door.");
        SHKODER_LOCATION.descriptionMap.put("depthentrance","This stairway leads to a higher level of the cave ruins.");
        SHKODER_LOCATION.descriptionMap.put("depthexit","These steps lead downwards to a deeper level of Shkoder.");
        SHKODER_LOCATION.descriptionMap.put("embers","Whatever was here has turned to ash.");
        SHKODER_LOCATION.descriptionMap.put("lockeddoor","This door is locked and you'll need a key to unlock it.");
        SHKODER_LOCATION.descriptionMap.put("pedestal","This is a fancy-looking piece of architecture from Shkoder's better days.");
        SHKODER_LOCATION.descriptionMap.put("specialwall","Although this ore is faintly glowing, it's sheer abundance made it almost worthless and as such, veins like these were commonly left unmined to serve as decoration.");
        SHKODER_LOCATION.descriptionMap.put("barricade","This is an old, dusty barricade of dry wood.");
        SHKODER_LOCATION.descriptionMap.put("bookshelf","This is a shelf full of unorganised books.");
        SHKODER_LOCATION.descriptionMap.put("specialfloor","The floor is nicely paved with floorboards.");
        SHKODER_LOCATION.descriptionMap.put("greentrap","");
        SHKODER_LOCATION.descriptionMap.put("offtrap","This trap has been triggered and will not activate anymore.");
        SHKODER_LOCATION.descriptionMap.put("orangetrap","");
        SHKODER_LOCATION.descriptionMap.put("yellowtrap","");
        SHKODER_LOCATION.descriptionMap.put("purpletrap","");
        SHKODER_LOCATION.descriptionMap.put("redtrap","");
        SHKODER_LOCATION.descriptionMap.put("bluetrap","");
        SHKODER_LOCATION.descriptionMap.put("beartrap","");
        SHKODER_LOCATION.descriptionMap.put("silvertrap","");
        SHKODER_LOCATION.descriptionMap.put("decofloor","The broken, buried remains of a resident of the mines lies here.");
        SHKODER_LOCATION.descriptionMap.put("lockeddepthexit","This entrance is heavily barred and probably requires some serious key action to open.");
        SHKODER_LOCATION.descriptionMap.put("unlockeddepthexit","Somehow, you found the key for this important-looking barrier and now it is open.");
        SHKODER_LOCATION.descriptionMap.put("sign","Try as you might, you cannot read the text from here.");
        SHKODER_LOCATION.descriptionMap.put("statue","A statue of some deity, king or hero.");
        SHKODER_LOCATION.descriptionMap.put("specialstatue","A statue of some deity, king or hero.");
        SHKODER_LOCATION.descriptionMap.put("alchemypot","This looks like the sort of cauldron that wizards use for brewing potions and other magical items.");
    }
    public static final Location INDOOR_CAVES_LOCATION = new Location(
            "Indoor Caves", "indoorCavesTileset", null, "shkoderWater", Region.SUDA, null){
        @Override
        public Animation getWallAnimation(int x, int y){
            return new ParticleAnimation.NullAnimation();
        }
    };
    static{
        ImageHandler.initializeInteriorIcons(INDOOR_CAVES_LOCATION);
    }
    public static final Location VILLAGE1_LOCATION = new Location(
            "Village1", "village1Tileset", null, "shkoderWater", Region.SUDA, null){
        @Override
        public Animation getWallAnimation(int x, int y){
            Integer[] c = MouseInterpreter.tileToPixel(x, y);
            ParticleAnimation a = GameSettings.TORCH_SETTING.get(ConstantFields.fireColor, ConstantFields.fireTrailColor);
            a.setXY(c[0]+6, c[1]+8);
            return a;
        }
    };
    static{
        ImageHandler.initializeInteriorIcons(VILLAGE1_LOCATION);
    }
    public static final Location CASTLE1_LOCATION = new Location(
            "Castle1", "castle1Tileset", null, "shkoderWater", Region.SUDA, null){
        @Override
        public Animation getWallAnimation(int x, int y){
            Integer[] c = MouseInterpreter.tileToPixel(x, y);
            ParticleAnimation a = GameSettings.TORCH_SETTING.get(ConstantFields.fireColor, ConstantFields.fireTrailColor);
            a.setXY(c[0]+6, c[1]+8);
            return a;
        }
    };
    static{
        ImageHandler.initializeInteriorIcons(CASTLE1_LOCATION);
    }
    
    
    public static final HashMap<String, Location> LOCATION_MAP = new HashMap<>();
    static{
        LOCATION_MAP.put("Shkoder", SHKODER_LOCATION);
        LOCATION_MAP.put("Indoor Caves", INDOOR_CAVES_LOCATION);
        LOCATION_MAP.put("Village1", VILLAGE1_LOCATION);
    }
    
}
