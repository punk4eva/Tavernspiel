
package items.consumables.scrolls;

import items.consumables.Scroll;
import items.consumables.ScrollProfile;

/**
 *
 * @author Adam Whittaker
 */
public abstract class WonderScroll extends Scroll{
    
    public WonderScroll(ScrollProfile sp){
        super("Scroll of Wonder", "This ancient scroll's mark has faded beyond recongnition over the years although you think you can still salvage the apparently positive effect that it had.", sp);
    }
    
}
