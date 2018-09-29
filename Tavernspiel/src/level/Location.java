
package level;

import animation.assets.GrassAnimation;
import creatureLogic.CreatureDistribution;
import items.builders.ItemBuilder;
import items.equipment.HeldWeapon;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.ImageHandler;
import items.equipment.weapons.*;

/**
 *
 * @author Adam Whittaker
 * 
 * This class stores all information for generating a Stage.
 */
public class Location{
    
    public final String name;
    public final ImageIcon tileset;
    public final ImageIcon waterImage;
    protected RoomDistribution roomDistrib;
    protected CreatureDistribution[] spawnDistribution;
    public LevelFeeling feeling = LevelFeeling.STANDARD;
    public final HashMap<String, ImageIcon> tilemap = new HashMap<>();
    public int depth = 1;
    public final Region region;
    
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
     */
    public Location(String n, BufferedImage tiles, BufferedImage water, Region r){
        name = n;
        waterImage = new ImageIcon(water);
        tileset = new ImageIcon(tiles);
        region = r;
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param water The water gen. chance.
     * @param r The Region.
     */
    public Location(String n, String tiles, String water, Region r){
        name = n;
        waterImage = new ImageIcon("graphics/tilesets/"+water+".png");
        tileset = new ImageIcon("graphics/tilesets/"+tiles+".png");
        region = r;
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param waterI The water image.
     * @param sp The CreatureDistributions.
     * @param r The Region.
     */
    public Location(String n, BufferedImage tiles, BufferedImage waterI, CreatureDistribution[] sp, Region r){
        name = n;
        waterImage = new ImageIcon(waterI);
        tileset = new ImageIcon(tiles);
        spawnDistribution = sp;
        region = r;
    }
    
    /*sudaizuita.map.put(0, new WeaponEntry(16, "Nunchaku", 0 , 64, "Two short wooden rods with a chain attaching them.|This weapon is rather fast and accurate. It has quite low durability.", 130, 11, 2, 8, 1, 1.2, 1.2, 0));
    sudaizuita.map.put(1, new WeaponEntry(17, "Kama", 16, 64, "A short sickle designed as a reliable, easy-to-use battle weapon.|This weapon is slightly slow and blocks slight amounts of damage.", 150, 12, 3, 9, 1, 1, 1, 1));
    sudaizuita.map.put(2, new WeaponEntry(18, "Tanto", 32, 64, "A long dagger, commonly used by assassins and the military.|This weapon is durable.", 160, 13, 4, 13, 1, 1, 1.1, 0));
    sudaizuita.map.put(3, new WeaponEntry(19, "Sai", 48, 64, "This oriental sword was designed to kill single opponents quickly and effectively.|This weapon is rather accurate.", 150, 14, 6, 18, 1, 1.1, 1.1, 0));
    sudaizuita.map.put(4, new WeaponEntry(20, "Kanabo", 64, 64, "This powerful instrument of war can only serve those powerful enough to wield it.|This weapon is extremely durable but slow and inaccurate. It blocks a tremendous amount of damage.", 210, 17, 10, 25, 1, 0.8, 0.9, 7));
    sudaizuita.map.put(5, new WeaponEntry(21, "Katana", 80, 64, "This light but powerful sword is the weapon of choice for most ninjas.|It is slightly deficient in durability.", 140, 15, 8, 23, 1, 1, 1.15, 0));
    sudaizuita.map.put(6, new WeaponEntry(22, "Naginata", 96, 64, "A long, heavy polearm capable of inflicting fatal wounds relatively quickly.|This weapon blocks some damage.", 150, 17, 11, 26, 1, 1, 1.1, 2));
    sudaizuita.map.put(7, new WeaponEntry(23, "Kusarigama", 112, 64, "A sickle attacked to a long metal chain.|It has a long reach but is very slow.", 150, 19, 17, 39, 2, 1, 0.6, 0));
    hurihuidoite.map.put(0, new WeaponEntry(24, "Catnails", 0,  80, "Five small metal edges with a wooden bead in the centre for fingers.|This weapon trades durability for accuracy and speed.", 135, 9, 1, 6, 1, 1.2, 1.15, 0));
    hurihuidoite.map.put(1, new WeaponEntry(25, "Counter Hammer", 16, 80, "A sturdy staff with a steel weight at each end, one for attack and one to counterbalance.", 160, 13, 2, 9, 1, 0.95, 1, 0));
    hurihuidoite.map.put(2, new WeaponEntry(26, "Ulysses", 32, 80, "Three blades chained to a well-crafted handle. Whip-like weapons such as this are effective at digging into flesh.|This weapon is slightly lacking in durability but is fast.", 142, 12, 2, 7, 1, 0.97, 1.12, 0));
    hurihuidoite.map.put(3, new WeaponEntry(27, "Saraga", 48, 80, "A curved blade favored by low ranking military officers as it is designed to balance both speed and strength.|This is a well balanced weapon.", 151, 15, 5, 16, 1, 1.1, 1.1, 0));
    hurihuidoite.map.put(4, new WeaponEntry(28, "Triaxe", 64, 80, "A pole with three serated axe blades capable of inflicting multiple injuries in one strike.|This weapon blocks damage, is far reaching and is slightly accurate but slightly slow.", 165, 17, 8, 22, 2, 1.1, 0.9, 3));
    hurihuidoite.map.put(5, new WeaponEntry(29, "Candle Staff", 80, 80, "This oriental varient of the trident has longer blades so can be used to chop as well as stab.|This weapon has a high reach and is slightly more durable.", 170, 18, 9, 23, 2, 1, 1, 0));
    hurihuidoite.map.put(6, new WeaponEntry(30, "Greatest Sword", 96, 80, "A beast of a great sword, this weapon takes inhuman strength to wield.|This weapon is quite durable but rather slow.", 180, 21, 20, 42, 1, 0.92, 0.75, 0));
    hurihuidoite.map.put(7, new WeaponEntry(31, "Fate's Scythe", 112, 80, "The unwieldly shape of this weapon means it needs a lot of skill to master. It is designed to quickly eliminate multiple opponents.|This weapon has insane durability, is well balanced and blocks some damage.", 205, 19, 12, 27, 1, 1.08, 1.08, 2));*/
    
    
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
    
    
    
    public static final Location SHKODER_LOCATION = 
            new Location("Shkoder", "shkoderTileset", "shkoderWater", Region.SUDA);
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
            "Indoor Caves", "indoorCavesTileset", "shkoderWater", Region.SUDA);
    static{
        ImageHandler.initializeInteriorIcons(INDOOR_CAVES_LOCATION);
    }
    public static final HashMap<String, Location> locationMap = new HashMap<>();
    static{
        locationMap.put("Shkoder", SHKODER_LOCATION);
        locationMap.put("Indoor Caves", INDOOR_CAVES_LOCATION);
    }
    
}
