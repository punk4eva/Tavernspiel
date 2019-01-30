
package tiles;

import level.Location;
import listeners.StepListener;

/**
 *
 * @author Adam Whittaker
 * 
 * A Tile that activates a secret power when stepped on.
 */
public abstract class Trap extends HiddenTile implements StepListener{
    
    public boolean reusable = false;
    public boolean used = false;
    
    public Trap(String tile, String desc, Location loc){
        super("floor", desc, true, false, true, tile, loc, 
                loc.feeling.trapVisibleChance.chance(), false, true, true);
    }
    
    public Trap(String tile, String desc, Location loc, boolean re){
        super("floor", desc, true, false, true, tile, loc, 
                loc.feeling.trapVisibleChance.chance(), false, true, true);
        reusable = re;
    }
    
    /**
     * Copies this trap.
     * @param loc The Location.
     * @return
     */
    public abstract Trap copy(Location loc);
    
}
