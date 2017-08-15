
package level;

import creatures.Creature;
import java.awt.Image;
import javax.swing.ImageIcon;
import listeners.AreaEventInitiator;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Location extends AreaEventInitiator{
    
    public final String name;
    public final Image tileset;
    protected Creature[] creatureSpawns = null; //null if no creatures spawn.
    protected Distribution waterGenChance = new Distribution(1, 20);
    protected Distribution grassGenChance = new Distribution(1, 20);
    protected RoomDistribution roomDistrib = null; //null if boss room.
    protected boolean waterBeforeGrass = true;
    public final String backgroundMusicPath;
    
    public Location(String n, Image tiles, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
    }
    
    public Location(String n, String tiles, String bmp){
        name = n;
        tileset = new ImageIcon("graphics/"+tiles+".png").getImage();
        backgroundMusicPath = bmp;
    }
    
    public Location(String n, Image tiles, Distribution water, Distribution grass, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
    }
    
    public Location(String n, Image tiles, Creature[] sp,Distribution water, Distribution grass, String bmp){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
    }
    
    public Location(String n, Image tiles, Creature[] sp, int difficulties, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        creatureSpawns = sp;
    }
    
    public Location(String n, Image tiles, Distribution water, Distribution grass, boolean wbg, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
        waterBeforeGrass = wbg;
    }
    
    public Location(String n, Image tiles, Creature[] sp,Distribution water, Distribution grass, boolean wbg, String bmp){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        waterGenChance = water;
        backgroundMusicPath = bmp;
        grassGenChance = grass;
        waterBeforeGrass = wbg;
    }
    
}
