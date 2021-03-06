
package tiles;

import blob.Blob;
import creatures.Creature;
import level.Location;

/**
 *
 * @author Adam Whittaker
 * 
 * A trap that releases a gas.
 */
public class GasTrap extends Trap{

    private Blob gas;
    
    public GasTrap(String tile, Location loc, Blob b){
        super(tile, loc);
    }

    @Override
    public void steppedOn(Creature c){
        gas.x = c.x;
        gas.y = c.y;
        c.area.addObject(gas);
    }

    @Override
    public Trap copy(Location loc){
        return new GasTrap(name, loc, gas);
    }
    
}
