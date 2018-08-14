
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.consumables.ScrollProfile;

/**
 *
 * @author Adam Whittaker
 */
public class JuxtapositionScroll extends Scroll{

    public JuxtapositionScroll(ScrollProfile sp){
        super("Scroll of Juxtaposition", "This mysterious parchment possesses the power to move the reader to a nearby location.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
