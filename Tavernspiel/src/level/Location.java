
package level;

import creatures.Creature;
import javax.swing.ImageIcon;
import logic.MultiDistribution;

/**
 *
 * @author Adam Whittaker
 */
public class Location{
    
    public String name;
    public ImageIcon tileset;
    public Creature[] creatureSpawns = null; //null if no creatures spawn.
    public MultiDistribution spawnDistrib = null;
    
    public Location(String n, ImageIcon tiles){
        name = n;
        tileset = tiles;
    }
    
    public Location(String n, ImageIcon tiles, Creature[] sp, MultiDistribution d){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        spawnDistrib = d;
    }
    
    public Location(String n, ImageIcon tiles, Creature[] sp, int difficulties){
        name = n;
        tileset = tiles;
        creatureSpawns = sp;
        spawnDistrib = MultiDistribution.getAllUniform(difficulties, sp.length);
    }
    
}
