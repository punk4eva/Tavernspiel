
package items.consumables.scrolls;

import creatures.Creature;
import items.builders.ScrollBuilder.ScrollRecord;
import items.consumables.Scroll;

/**
 *
 * @author Adam Whittaker
 */
public class AnimationScroll extends Scroll{

    public AnimationScroll(ScrollRecord sp){
        super("Scroll of Animation", "When in need of urgent aid in battle one must read this scroll and all their problems shall be solved!|Use multiple scrolls close to the summon to empower it.|If the monster grows to a certain size, it can be possessed and can grow exponentially larger with the hyaili that it generates.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
