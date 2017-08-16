
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
    public final Image waterImage;
    protected Creature[] creatureSpawns = null; //null if no creatures spawn.
    protected Distribution waterGenChance = new Distribution(1, 20);
    protected Distribution grassGenChance = new Distribution(1, 20);
    protected RoomDistribution roomDistrib = null; //null if boss room.
    protected boolean waterBeforeGrass = true;
    public final String backgroundMusicPath;
    
    public Location(String n, Image tiles, Image water, String bmp){
        name = n;
        waterImage = water;
        tileset = tiles;
        backgroundMusicPath = bmp;
    }
    
    public Location(String n, String tiles, String water, String bmp){
        name = n;
        waterImage = new ImageIcon("graphics/"+water+".png").getImage();
        tileset = new ImageIcon("graphics/"+tiles+".png").getImage();
        backgroundMusicPath = bmp;
    }
    
    public Location(String n, Image tiles, Image waterI, Distribution water, Distribution grass, String bmp){
        name = n;
        tileset = tiles;
        waterImage = waterI;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
    }
    
    public Location(String n, Image tiles, Image waterI, Creature[] sp, Distribution water, Distribution grass, String bmp){
        name = n;
        waterImage = waterI;
        tileset = tiles;
        creatureSpawns = sp;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
    }
    
    public Location(String n, Image tiles,  Image water, Creature[] sp, int difficulties, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        waterImage = water;
        creatureSpawns = sp;
    }
    
    public Location(String n, Image tiles,  Image waterI, Distribution water, Distribution grass, boolean wbg, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
        waterImage = waterI;
        waterBeforeGrass = wbg;
    }
    
    public Location(String n, Image tiles,  Image waterI, Creature[] sp, Distribution water, Distribution grass, boolean wbg, String bmp){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        waterGenChance = water;
        backgroundMusicPath = bmp;
        waterImage = waterI;
        grassGenChance = grass;
        waterBeforeGrass = wbg;
    }
    
}
