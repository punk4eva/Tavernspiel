
package level;

import creatureLogic.CreatureDistribution;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import javax.swing.ImageIcon;
import logic.Distribution;

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
    final WeaponIndex weaponIndex;
    
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
    
    static class WeaponIndex{
        
        HashMap<Integer, WeaponEntry> map = new HashMap<>();
        
        private WeaponIndex(){}
        
        private final static WeaponIndex 
        kirikisande = new WeaponIndex(),
        kyoukuOkeshte = new WeaponIndex(),
        sudaizuita = new WeaponIndex(),
        hurihuidoite = new WeaponIndex();
        static{
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 0, 32));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 16, 32));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 32, 32));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 48, 32));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 64, 32));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 80, 32));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 96, 32));
            kirikisande.map.put(0, new WeaponEntry("UNFINISHED", 112, 32));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 0 , 48));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 16, 48));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 32, 48));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 48, 48));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 64, 48));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 80, 48));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 96, 48));
            kyoukuOkeshte.map.put(0, new WeaponEntry("UNFINISHED", 112, 48));
            sudaizuita.map.put(0, new WeaponEntry("Nunchaku", 0 , 64));
            sudaizuita.map.put(0, new WeaponEntry("Kama", 16, 64));
            sudaizuita.map.put(0, new WeaponEntry("Tanto", 32, 64));
            sudaizuita.map.put(0, new WeaponEntry("Sai", 48, 64));
            sudaizuita.map.put(0, new WeaponEntry("Kanabo", 64, 64));
            sudaizuita.map.put(0, new WeaponEntry("Katana", 80, 64));
            sudaizuita.map.put(0, new WeaponEntry("Naginata", 96, 64));
            sudaizuita.map.put(0, new WeaponEntry("Kusarigama", 112, 64));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 0,  80));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 16, 80));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 32, 80));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 48, 80));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 64, 80));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 80, 80));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 96, 80));
            hurihuidoite.map.put(0, new WeaponEntry("UNFINISHED", 112, 80));
        }
        
        static WeaponIndex getIndex(int i){
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
        final Dimension dim;
        final String name;
        
        WeaponEntry(String n, int x, int y){
            name = n;
            dim = new Dimension(x, y);
        }
    }
    
    WeaponEntry getEntry(Integer i){
            return weaponIndex.map.get(i);
    }
    
}
