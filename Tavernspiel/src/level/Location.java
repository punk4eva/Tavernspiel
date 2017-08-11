
package level;

import creatures.Creature;
import javax.swing.ImageIcon;
import listeners.AreaEventInitiator;
import logic.Distribution;
import logic.MultiDistribution;

/**
 *
 * @author Adam Whittaker
 */
public class Location extends AreaEventInitiator{
    
    public final String name;
    public final ImageIcon tileset;
    protected Creature[] creatureSpawns = null; //null if no creatures spawn.
    public MultiDistribution stageSpawnDistrib = null;
    protected Distribution waterGenChance = new Distribution(1, 20);
    protected Distribution grassGenChance = new Distribution(1, 20);
    protected RoomDistribution roomDistrib = null; //null if boss room.
    protected boolean waterBeforeGrass = true;
    public final String backgroundMusicPath;
    
    public Location(String n, ImageIcon tiles, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
    }
    
    public Location(String n, String tiles, String bmp){
        name = n;
        tileset = new ImageIcon("graphics/"+tiles+".png");
        backgroundMusicPath = bmp;
    }
    
    public Location(String n, ImageIcon tiles, Distribution water, Distribution grass, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
    }
    
    public Location(String n, ImageIcon tiles, Creature[] sp, MultiDistribution d, Distribution water, Distribution grass, String bmp){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        stageSpawnDistrib = d;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
    }
    
    public Location(String n, ImageIcon tiles, Creature[] sp, int difficulties, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        creatureSpawns = sp;
        stageSpawnDistrib = MultiDistribution.getAllUniform(difficulties, sp.length);
    }
    
    public Location(String n, ImageIcon tiles, Distribution water, Distribution grass, boolean wbg, String bmp){
        name = n;
        tileset = tiles;
        backgroundMusicPath = bmp;
        waterGenChance = water;
        grassGenChance = grass;
        waterBeforeGrass = wbg;
    }
    
    public Location(String n, ImageIcon tiles, Creature[] sp, MultiDistribution d, Distribution water, Distribution grass, boolean wbg, String bmp){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        stageSpawnDistrib = d;
        waterGenChance = water;
        backgroundMusicPath = bmp;
        grassGenChance = grass;
        waterBeforeGrass = wbg;
    }
    
}
