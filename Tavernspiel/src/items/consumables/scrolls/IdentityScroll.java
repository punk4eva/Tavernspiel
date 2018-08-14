
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.consumables.ScrollProfile;

/**
 *
 * @author Adam Whittaker
 */
public class IdentityScroll extends Scroll{

    public IdentityScroll(ScrollProfile sp){
        super("Scroll of Identity", "The curious charm of this scroll increases the intelligence of the reader, revealing the secrets of a single item of their choice to them.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
