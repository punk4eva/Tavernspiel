
package level;

import creatureLogic.CreatureDistribution;
import java.awt.Image;
import java.io.Serializable;
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
    public final Image tileset;
    public final Image waterImage;
    protected Distribution waterGenChance = new Distribution(1, 20);
    protected Distribution grassGenChance = new Distribution(1, 20);
    protected RoomDistribution[] roomDistrib = null; //null if boss room.
    protected CreatureDistribution[] spawnDistribution;
    protected boolean waterBeforeGrass = true;
    public final String backgroundMusicPath;
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param water The water gen. chance.
     * @param bmp The path for the background music.
     */
    public Location(String n, Image tiles, Image water, String bmp){
        name = n;
        waterImage = water;
        tileset = tiles;
        backgroundMusicPath = bmp;
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param water The water gen. chance.
     * @param bmp The path for the background music.
     */
    public Location(String n, String tiles, String water, String bmp){
        name = n;
        waterImage = new ImageIcon("graphics/"+water+".png").getImage();
        tileset = new ImageIcon("graphics/"+tiles+".png").getImage();
        backgroundMusicPath = bmp;
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param waterI The water image.
     * @param water The water gen. chance.
     * @param grass The grass gen. chance.
     * @param bmp The path for the background music.
     */
    public Location(String n, Image tiles, Image waterI, Distribution water, Distribution grass, String bmp){
        name = n;
        tileset = tiles;
        waterImage = waterI;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
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
     */
    public Location(String n, Image tiles, Image waterI, CreatureDistribution[] sp, Distribution water, Distribution grass, String bmp){
        name = n;
        waterImage = waterI;
        tileset = tiles;
        spawnDistribution = sp;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param tiles The tileset.
     * @param water The water image.
     * @param sp The CreatureDistributions.
     * @param bmp The path for the background music.
     */
    public Location(String n, Image tiles, Image water, CreatureDistribution[] sp, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        waterImage = water;
        spawnDistribution = sp;
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
     */
    public Location(String n, Image tiles, Image waterI, Distribution water, Distribution grass, boolean wbg, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
        waterImage = waterI;
        waterBeforeGrass = wbg;
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
     */
    public Location(String n, Image tiles, Image waterI, CreatureDistribution[] sp, Distribution water, Distribution grass, boolean wbg, String bmp){
        name = n;
        tileset = tiles;
        spawnDistribution = sp;
        waterGenChance = water;
        backgroundMusicPath = bmp;
        waterImage = waterI;
        grassGenChance = grass;
        waterBeforeGrass = wbg;
    }
    
}
