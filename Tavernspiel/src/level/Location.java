
package level;

import creatures.Creature;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.MultiDistribution;

/**
 *
 * @author Adam Whittaker
 */
public class Location{
    
    public String name;
    protected ImageIcon tileset;
    protected Creature[] creatureSpawns = null; //null if no creatures spawn.
    protected MultiDistribution stageSpawnDistrib = null;
    protected Distribution waterGenChance = new Distribution(1, 20);
    protected Distribution grassGenChance = new Distribution(1, 20);
    protected boolean waterBeforeGrass = true;
    
    public Location(String n, int st, ImageIcon tiles){
        name = n;
        tileset = tiles;
    }
    
    public Location(String n, int st, ImageIcon tiles, Distribution water, Distribution grass){
        name = n;
        tileset = tiles;
        waterGenChance = water;
        grassGenChance = grass;
    }
    
    public Location(String n, int st, ImageIcon tiles, Creature[] sp, MultiDistribution d, Distribution water, Distribution grass){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        stageSpawnDistrib = d;
        waterGenChance = water;
        grassGenChance = grass;
    }
    
    public Location(String n, ImageIcon tiles, Creature[] sp, int difficulties){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        stageSpawnDistrib = MultiDistribution.getAllUniform(difficulties, sp.length);
    }
    
    public Location(String n, int st, ImageIcon tiles, Distribution water, Distribution grass, boolean wbg){
        name = n;
        tileset = tiles;
        waterGenChance = water;
        grassGenChance = grass;
        waterBeforeGrass = wbg;
    }
    
    public Location(String n, int st, ImageIcon tiles, Creature[] sp, MultiDistribution d, Distribution water, Distribution grass, boolean wbg){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        stageSpawnDistrib = d;
        waterGenChance = water;
        grassGenChance = grass;
        waterBeforeGrass = wbg;
    }
    
}
