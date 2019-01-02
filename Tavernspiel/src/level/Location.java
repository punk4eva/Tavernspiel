
package level;

import animation.Animation;
import animation.assets.GrassAnimation;
import blob.ParticleAnimation;
import creatureLogic.CreatureDistribution;
import gui.mainToolbox.MouseInterpreter;
import items.builders.ItemBuilder;
import items.equipment.HeldWeapon;
import java.util.HashMap;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.ImageHandler;
import items.equipment.weapons.*;
import java.awt.Dimension;
import java.util.List;
import java.util.function.Function;
import logic.ConstantFields;
import logic.GameSettings;

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
    //@Unfinished
    public final ImageIcon backgroundImage = null;
    
    public GrassAnimation lowGrass;
    public GrassAnimation highGrass;
    
    public enum Region{
        KIRI(0, new Distribution(new int[]{})),
        KYOU(1, new Distribution(new int[]{})),
        SUDA(2, new Distribution(new int[]{10, 10, 12, 8, 6, 1, 2, 2})),
        HURI(3, new Distribution(new int[]{}));
        
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
     * @param water The water gen. chance.
     * @param r The Region.
     * @param struct The room generation algorithm.
     */
    public Location(String n, String tiles, String water, Region r, Function<List<Room>, RoomStructure> struct){
        name = n;
        waterImage = new ImageIcon("graphics/tilesets/"+water+".png");
        tileset = new ImageIcon("graphics/tilesets/"+tiles+".png");
        region = r;
        structure = struct;
    }
    
    
    
    /**
     * Retrieves a random WeaponEntry based on their respective rarities.
     * @return
     */
    public HeldWeapon getRandomWeapon(){
        switch(region.code){
            case 0: switch((int)region.weaponRarities.next()){
                case 0:
                case 1:
                case 2:
                case 3: 
                case 4:
                case 5:
                case 6:
                default:
            }
            case 1: switch((int)region.weaponRarities.next()){
                case 0:
                case 1:
                case 2:
                case 3: 
                case 4:
                case 5:
                case 6:
                default:
            }
            case 2: switch((int)region.weaponRarities.next()){
                case 0: return new Nunchaku();
                case 1: return new Kama();
                case 2: return new Tanto();
                case 3: return new Sai();
                case 4: return new Kanabo();
                case 5: return new Katana();
                case 6: return new Naginata();
                default: return new Kusarigama();
            }
            case 3: switch((int)region.weaponRarities.next()){
                case 0: return new Catnails();
                case 1: return new CounterHammer();
                case 2: return new Ulysses();
                case 3: return new Saraga();
                case 4: return new Triaxe();
                case 5: return new CandleStaff();
                case 6: return new GreatestSword();
                default: return new FateScythe();
            }
            default: return null;
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
        return ItemBuilder.getIcon(locationMap.get(loc).region.code*16, 16);
    }
    
    /**
     * Retrieves the Locked Chest Icon for this Region.
     * @param loc The name of the Location
     * @return
     */
    public static ImageIcon getLockedChestIcon(String loc){
        return ItemBuilder.getIcon(locationMap.get(loc).region.code*16+64, 16);
    }
    
    /**
     * Retrieves the Skeletal Remains Icon for this Region.
     * @param loc The name of the Location
     * @return
     */
    public static ImageIcon getSkeletalRemainsIcon(String loc){
        return ItemBuilder.getIcon(locationMap.get(loc).region.code*16+64, 0);
    }
    
    /**
     * Retrieves the Locked Crystal Icon for this Region.
     * @param loc The name of the Location
     * @return
     */
    public static ImageIcon getCrystalChestIcon(String loc){
        return ItemBuilder.getIcon(locationMap.get(loc).region.code*16, 0);
    }
    
    public abstract Animation getWallAnimation(int x, int y);
    
    
    
    public static final Location SHKODER_LOCATION = 
            new Location("Shkoder", "shkoderTileset", "shkoderWater", Region.SUDA, (list) -> new RoomStructure.SpiderCorridor(new Dimension(80, 80), Location.SHKODER_LOCATION, list, 15, true)){
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
    }
    public static final Location INDOOR_CAVES_LOCATION = new Location(
            "Indoor Caves", "indoorCavesTileset", "shkoderWater", Region.SUDA, null){
        @Override
        public Animation getWallAnimation(int x, int y){
            return new ParticleAnimation.NullAnimation();
        }
    };
    static{
        ImageHandler.initializeInteriorIcons(INDOOR_CAVES_LOCATION);
    }
    public static final Location VILLAGE1_LOCATION = new Location(
            "Village1", "village1Tileset", "shkoderWater", Region.SUDA, null){
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
    public static final HashMap<String, Location> locationMap = new HashMap<>();
    static{
        locationMap.put("Shkoder", SHKODER_LOCATION);
        locationMap.put("Indoor Caves", INDOOR_CAVES_LOCATION);
        locationMap.put("Village1", VILLAGE1_LOCATION);
    }
    
}
