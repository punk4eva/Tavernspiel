
package level;

import animation.assets.GrassAnimation;
import creatureLogic.CreatureDistribution;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.ImageHandler;
import logic.Utils.Unfinished;

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
    protected RoomDistribution roomDistrib; //null if boss room.
    protected CreatureDistribution[] spawnDistribution;
    public LevelFeeling feeling = LevelFeeling.STANDARD;
    public final HashMap<String, ImageIcon> tilemap = new HashMap<>();
    public final WeaponIndex weaponIndex;
    private final Distribution armourDistrib = new Distribution(new int[]{12,20,6,3,1});
    public int depth = 1;
    
    public GrassAnimation lowGrass;
    public GrassAnimation highGrass;
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param water The water gen. chance.
     * @param a Represents the country (0-3).
     */
    public Location(String n, BufferedImage tiles, BufferedImage water, int a){
        name = n;
        waterImage = new ImageIcon(water);
        tileset = new ImageIcon(tiles);
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param water The water gen. chance.
     * @param a Represents the country (0-3).
     */
    public Location(String n, String tiles, String water, int a){
        name = n;
        waterImage = new ImageIcon("graphics/tilesets/"+water+".png");
        tileset = new ImageIcon("graphics/tilesets/"+tiles+".png");
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param waterI The water image.
     * @param sp The CreatureDistributions.
     * @param a Represents the country (0-3).
     */
    public Location(String n, BufferedImage tiles, BufferedImage waterI, CreatureDistribution[] sp, int a){
        name = n;
        waterImage = new ImageIcon(waterI);
        tileset = new ImageIcon(tiles);
        spawnDistribution = sp;
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * This class represents the rarities and Image locations of Weapons in
     * different Areas.
     */
    @Unfinished("Fill in weapon stats.")
    public static class WeaponIndex{
        
        private final HashMap<Integer, WeaponEntry> map = new HashMap<>();
        private final Distribution rarities;
        
        private WeaponIndex(int... r){
            rarities = new Distribution(r);
        }
        
        private final static WeaponIndex 
        kirikisande = new WeaponIndex(),
        kyoukuOkeshte = new WeaponIndex(),
        sudaizuita = new WeaponIndex(10, 10, 12, 8, 6, 1, 2, 2),
        hurihuidoite = new WeaponIndex();
        static{
            kirikisande.map.put(0, new WeaponEntry(0, "UNFINISHED", 0, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(1, new WeaponEntry(1, "UNFINISHED", 16, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(2, new WeaponEntry(2, "UNFINISHED", 32, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(3, new WeaponEntry(3, "UNFINISHED", 48, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(4, new WeaponEntry(4, "UNFINISHED", 64, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(5, new WeaponEntry(5, "UNFINISHED", 80, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(6, new WeaponEntry(6, "UNFINISHED", 96, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(7, new WeaponEntry(7, "UNFINISHED", 112, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(0, new WeaponEntry(8, "UNFINISHED", 0 , 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(1, new WeaponEntry(9, "UNFINISHED", 16, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(2, new WeaponEntry(10, "UNFINISHED", 32, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(3, new WeaponEntry(11, "UNFINISHED", 48, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(4, new WeaponEntry(12, "UNFINISHED", 64, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(5, new WeaponEntry(13, "UNFINISHED", 80, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(6, new WeaponEntry(14, "UNFINISHED", 96, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(7, new WeaponEntry(15, "UNFINISHED", 112, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            sudaizuita.map.put(0, new WeaponEntry(16, "Nunchaku", 0 , 64, "Two short wooden rods with a chain attaching them.|This weapon is rather fast and accurate. It has quite low durability.", 130, 11, 2, 8, 1, 1.2, 1.2, 0));
            sudaizuita.map.put(1, new WeaponEntry(17, "Kama", 16, 64, "A short sickle designed as a reliable, easy-to-use battle weapon.|This weapon is slightly slow and blocks slight amounts of damage.", 150, 12, 3, 9, 1, 1, 1, 1));
            sudaizuita.map.put(2, new WeaponEntry(18, "Tanto", 32, 64, "A long dagger, commonly used by assassins and the military.|This weapon is durable.", 160, 13, 4, 13, 1, 1, 1.1, 0));
            sudaizuita.map.put(3, new WeaponEntry(19, "Sai", 48, 64, "This oriental sword was designed to kill single opponents quickly and effectively.|This weapon is rather accurate.", 150, 14, 6, 18, 1, 1.1, 1.1, 0));
            sudaizuita.map.put(4, new WeaponEntry(20, "Kanabo", 64, 64, "This powerful instrument of war can only serve those powerful enough to wield it.|This weapon is extremely durable but slow and inaccurate. It blocks a tremendous amount of damage.", 210, 17, 10, 25, 1, 0.8, 0.9, 7));
            sudaizuita.map.put(5, new WeaponEntry(21, "Katana", 80, 64, "This light but powerful sword is the weapon of choice for most ninjas.|It is slightly deficient in durability.", 140, 15, 8, 23, 1, 1, 1.15, 0));
            sudaizuita.map.put(6, new WeaponEntry(22, "Naginata", 96, 64, "A long, heavy polearm capable of inflicting fatal wounds relatively quickly.|This weapon blocks some damage.", 150, 17, 11, 26, 1, 1, 1.1, 2));
            sudaizuita.map.put(7, new WeaponEntry(23, "Kusarigama", 112, 64, "A sickle attacked to a long metal chain.|It has a long reach but is very slow.", 150, 19, 17, 39, 2, 1, 0.6, 0));
            hurihuidoite.map.put(0, new WeaponEntry(24, "UNFINISHED", 0,  80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(1, new WeaponEntry(25, "UNFINISHED", 16, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(2, new WeaponEntry(26, "UNFINISHED", 32, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(3, new WeaponEntry(27, "UNFINISHED", 48, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(4, new WeaponEntry(28, "UNFINISHED", 64, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(5, new WeaponEntry(29, "UNFINISHED", 80, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(6, new WeaponEntry(30, "UNFINISHED", 96, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(7, new WeaponEntry(31, "UNFINISHED", 112, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
        }
        
        private static WeaponIndex getIndex(int i){
            switch(i){
                case 0: return kirikisande;
                case 1: return kyoukuOkeshte;
                case 2: return sudaizuita;
                case 3: return hurihuidoite;
                default: return null;
            }
        }
        
        /**
         * Retrieves the Entry from the ID.
         * @param id
         * @return
         */
        public static WeaponEntry getEntry(int id){
            int i = id%8;
            switch(Math.floorDiv(id, 8)){
                case 0: return kirikisande.map.get(i);
                case 1: return kyoukuOkeshte.map.get(i);
                case 2: return sudaizuita.map.get(i);
                default: return hurihuidoite.map.get(i);
            }
        }
        
    }
    
    /**
     * This class represents the statistics of a Weapon.
     */
    public static class WeaponEntry implements Serializable{
        
        private final static long serialVersionUID = 58843242548889L;
        
        public final int x, y, re, durability, strength, ID;
        public final String name, description;
        public final Distribution distrib;
        public final double ac, sp, bl;
        
        WeaponEntry(int id, String n, int _x, int _y, String desc, int dur, int st, int lo, int up, int r, double a, double s, double b){
            name = n;
            ID = id;
            distrib = new Distribution(lo, up);
            durability = dur;
            strength = st;
            description = desc;
            x = _x;
            y = _y;
            re = r;
            sp = s;
            ac = a;
            bl = b;
        }
    }
    
    /**
     * Retrieves a random WeaponEntry based on their respective rarities.
     * @return
     */
    public WeaponEntry getRandomWeaponEntry(){
        return weaponIndex.map.get((int)weaponIndex.rarities.next());
    }
    
    /**
     * Returns a random type of armour based on their respective rarities.
     * @return
     */
    public String getArmourType(){
        switch((int)armourDistrib.next()){
            case 0: return "cloth";
            case 1: return "leather";
            case 2: return "mail";
            case 3: return "scale";
            default: return "plate";
        }
    }
    
    public ImageIcon getImage(String str){
        return tilemap.get(str);
    }
    
    
    
    public static final Location SHKODER_LOCATION = 
            new Location("Shkoder", "shkoderTileset", "shkoderWater", 2);
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
            "Indoor Caves", "indoorCavesTileset", "shkoderWater", 2);
    static{
        ImageHandler.initializeInteriorIcons(INDOOR_CAVES_LOCATION);
    }
    public static final HashMap<String, Location> locationMap = new HashMap<>();
    static{
        locationMap.put("Shkoder", SHKODER_LOCATION);
        locationMap.put("Indoor Caves", INDOOR_CAVES_LOCATION);
    }
    
}
