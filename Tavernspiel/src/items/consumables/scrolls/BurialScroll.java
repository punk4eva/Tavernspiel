
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.LocationSpecificScroll;
import items.consumables.ScrollProfile;

/**
 *
 * @author Adam Whittaker
 */
public class BurialScroll extends LocationSpecificScroll{

    public BurialScroll(ScrollProfile sp){
        super("Scroll of Burial", "Buries everything in the effect area in the ground.", sp);
    }

    @Override
    public boolean use(Creature c, int x, int y){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
