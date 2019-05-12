
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.builders.ScrollBuilder.ScrollRecord;

/**
 *
 * @author Adam Whittaker
 */
public class InfiniteHorseScroll extends Scroll{

    public InfiniteHorseScroll(ScrollRecord sp){
        super("Scroll of Infinite Horse", "The rare summoning magic on this parchment will summon a majestic beast which can be used to traverse large distances in a short period of time.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
