
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
    
    public Trap(String tile, Location loc){
        super(loc, !loc.feeling.trapVisibleChance.chance(), "floor", true, false, true, tile, true, false, true);
    }
    
    public Trap(String tile, Location loc, boolean re){
        super(loc, !loc.feeling.trapVisibleChance.chance(), "floor", true, false, true, tile, true, false, true);
        reusable = re;
    }
    
    /**
     * Copies this trap.
     * @param loc The Location.
     * @return
     */
    public abstract Trap copy(Location loc);
    
}
