
package items.consumables.scrolls;

import items.builders.ScrollBuilder.ScrollRecord;
import items.consumables.Scroll;

/**
 *
 * @author Adam Whittaker
 */
public abstract class WonderScroll extends Scroll{
    
    public WonderScroll(ScrollRecord sp){
        super("Scroll of Wonder", "This ancient scroll's mark has faded beyond recongnition over the years although you think you can still salvage the apparently positive effect that it had.", sp);
    }
    
}
