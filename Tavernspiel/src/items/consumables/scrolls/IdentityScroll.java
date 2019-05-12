
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.builders.ScrollBuilder.ScrollRecord;

/**
 *
 * @author Adam Whittaker
 */
public class IdentityScroll extends Scroll{

    public IdentityScroll(ScrollRecord sp){
        super("Scroll of Identity", "The curious charm of this scroll increases the intelligence of the reader, revealing the secrets of a single item of their choice to them.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
