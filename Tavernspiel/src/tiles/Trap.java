
package tiles;

import level.Location;
import listeners.StepListener;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Trap extends HiddenTile implements StepListener{
    
    public boolean reusable = false;
    public boolean used = false;
    
    public Trap(String tile, Location loc){
        super("floor", true, false, true, tile, loc, 
                loc.feeling.trapVisibleChance.chance(), false, true, true);
    }
    
    public Trap(String tile, Location loc, boolean re){
        super("floor", true, false, true, tile, loc, 
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
