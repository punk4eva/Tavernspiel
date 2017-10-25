
package level;

import creatureLogic.CreatureDistribution;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class stores all information for generating a Stage.
 */
public class Location implements Serializable{
    
    private final static long serialVersionUID = 1965687765;
    
    public final String name;
    public final ImageIcon tileset;
    public final ImageIcon waterImage;
    protected Distribution waterGenChance = new Distribution(1, 20);
    protected Distribution grassGenChance = new Distribution(1, 20);
    protected RoomDistribution[] roomDistrib = null; //null if boss room.
    protected CreatureDistribution[] spawnDistribution;
    protected boolean waterBeforeGrass = true;
    public final String backgroundMusicPath;
    public final HashMap<String, ImageIcon> tilemap = new HashMap<>();
    public int difficulty;
    public final WeaponIndex weaponIndex;
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param water The water gen. chance.
     * @param bmp The path for the background music.
     * @param a Represents the country (0-3).
     */
    public Location(String n, BufferedImage tiles, BufferedImage water, String bmp, int a){
        name = n;
        waterImage = new ImageIcon(water);
        tileset = new ImageIcon(tiles);
        backgroundMusicPath = bmp;
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param water The water gen. chance.
     * @param bmp The path for the background music.
     * @param a Represents the country (0-3).
     */
    public Location(String n, String tiles, String water, String bmp, int a){
        name = n;
        waterImage = new ImageIcon("graphics/"+water+".png");
        tileset = new ImageIcon("graphics/"+tiles+".png");
        backgroundMusicPath = bmp;
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param waterI The water image.
     * @param water The water gen. chance.
     * @param grass The grass gen. chance.
     * @param bmp The path for the background music.
     * @param a Represents the country (0-3).
     */
    public Location(String n, BufferedImage tiles, BufferedImage waterI, Distribution water, Distribution grass, String bmp, int a){
        name = n;
        tileset = new ImageIcon(tiles);
        waterImage = new ImageIcon(waterI);
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param waterI The water image.
     * @param sp The CreatureDistributions.
     * @param water The water gen. chance.
     * @param grass The grass gen. chance.
     * @param bmp The path for the background music.
     * @param a Represents the country (0-3).
     */
    public Location(String n, BufferedImage tiles, BufferedImage waterI, CreatureDistribution[] sp, Distribution water, Distribution grass, String bmp, int a){
        name = n;
        waterImage = new ImageIcon(waterI);
        tileset = new ImageIcon(tiles);
        spawnDistribution = sp;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param water The water image.
     * @param sp The CreatureDistributions.
     * @param bmp The path for the background music.
     * @param a Represents the country (0-3).
     */
    public Location(String n, BufferedImage tiles, BufferedImage water, CreatureDistribution[] sp, String bmp, int a){
        name = n;
        tileset = new ImageIcon(tiles);
        backgroundMusicPath = bmp;
        waterImage = new ImageIcon(water);
        spawnDistribution = sp;
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param waterI The water image.
     * @param water The water gen. chance.
     * @param grass The grass gen. chance.
     * @param wbg Whether water should be generated before grass.
     * @param bmp The path for the background music.
     * @param a Represents the country (0-3).
     */
    public Location(String n, BufferedImage tiles, BufferedImage waterI, Distribution water, Distribution grass, boolean wbg, String bmp, int a){
        name = n;
        tileset = new ImageIcon(tiles);
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
        waterImage = new ImageIcon(waterI);
        waterBeforeGrass = wbg;
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param waterI The water image.
     * @param water The water gen. chance.
     * @param grass The grass gen. chance.
     * @param wbg Whether water should be generated before grass.
     * @param bmp The path for the background music.
     * @param a Represents the country (0-3).
     */
    public Location(String n, String tiles, String waterI, Distribution water, Distribution grass, boolean wbg, String bmp, int a){
        name = n;
        tileset = new ImageIcon(tiles);
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
        waterImage = new ImageIcon(waterI);
        waterBeforeGrass = wbg;
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param waterI The water image.
     * @param sp The CreatureDistributions.
     * @param water The water gen. chance.
     * @param grass The grass gen. chance.
     * @param wbg Whether water should be generated before grass.
     * @param bmp The path for the background music.
     * @param a Represents the country (0-3).
     */
    public Location(String n, BufferedImage tiles, BufferedImage waterI, CreatureDistribution[] sp, Distribution water, Distribution grass, boolean wbg, String bmp, int a){
        name = n;
        tileset = new ImageIcon(tiles);
        spawnDistribution = sp;
        waterGenChance = water;
        backgroundMusicPath = bmp;
        waterImage = new ImageIcon(waterI);
        grassGenChance = grass;
        waterBeforeGrass = wbg;
        weaponIndex = WeaponIndex.getIndex(a);
    }
    
    @Unfinished("Fill in weapon stats.")
    public static class WeaponIndex{
        
        private final HashMap<Integer, WeaponEntry> map = new HashMap<>();
        public final int[] rarities;
        
        private WeaponIndex(int... r){
            rarities = r;
        }
        
        private final static WeaponIndex 
        kirikisande = new WeaponIndex(),
        kyoukuOkeshte = new WeaponIndex(),
        sudaizuita = new WeaponIndex(10, 10, 12, 8, 6, 1, 2, 2),
        hurihuidoite = new WeaponIndex();
        static{
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 0, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 16, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 32, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 48, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 64, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 80, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 96, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 112, 32, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 0 , 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 16, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 32, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 48, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 64, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 80, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 96, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 112, 48, "", -1, -1, -2, -2, 1, 1, 1, 0));
            sudaizuita.map.put(0, new WeaponEntry("Nunchaku", 0 , 64, "Two short wooden rods with a chain attaching them.|This weapon is rather fast and accurate. It has quite low durability.", 130, 11, 2, 8, 1, 1.2, 1.2, 0));
            sudaizuita.map.put(0, new WeaponEntry("Kama", 16, 64, "A short sickle designed as a reliable, easy-to-use battle weapon.|This weapon is slightly slow and blocks slight amounts of damage.", 150, 12, 3, 9, 1, 1, 1, 1));
            sudaizuita.map.put(0, new WeaponEntry("Tanto", 32, 64, "A long dagger, commonly used by assassins and the military.|This weapon is durable.", 160, 13, 4, 13, 1, 1, 1.1, 0));
            sudaizuita.map.put(0, new WeaponEntry("Sai", 48, 64, "This oriental sword was designed to kill single opponents quickly and effectively.|This weapon is rather accurate.", 150, 14, 6, 18, 1, 1.1, 1.1, 0));
            sudaizuita.map.put(0, new WeaponEntry("Kanabo", 64, 64, "This powerful instrument of war can only serve those powerful enough to wield it.|This weapon is extremely durable but slow and inaccurate. It blocks a tremendous amount of damage.", 210, 17, 10, 25, 1, 0.8, 0.9, 7));
            sudaizuita.map.put(0, new WeaponEntry("Katana", 80, 64, "This light but powerful sword is the weapon of choice for most ninjas.|It is slightly deficient in durability.", 140, 15, 8, 23, 1, 1, 1.15, 0));
            sudaizuita.map.put(0, new WeaponEntry("Naginata", 96, 64, "A long, heavy polearm capable of inflicting fatal wounds relatively quickly.|This weapon blocks some damage.", 150, 17, 11, 26, 1, 1, 1.1, 2));
            sudaizuita.map.put(0, new WeaponEntry("Kusarigama", 112, 64, "A sickle attacked to a long metal chain.|It has a long reach but is very slow.", 150, 19, 17, 39, 2, 1, 0.6, 0));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 0,  80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 16, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 32, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 48, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 64, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 80, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 96, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 112, 80, "", -1, -1, -2, -2, 1, 1, 1, 0));
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
        
    }
    
    public static class WeaponEntry{
        public final int x, y, re, durability, strength;
        public final String name, description;
        public final Distribution distrib;
        public final double ac, sp, bl;
        
        WeaponEntry(String n, int _x, int _y, String desc, int dur, int st, int lo, int up, int r, double a, double s, double b){
            name = n;
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
    
    public WeaponEntry getWeaponEntry(Integer i){
            return weaponIndex.map.get(i);
    }
    
}
