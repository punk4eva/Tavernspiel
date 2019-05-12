
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.LocationSpecificScroll;
import items.builders.ScrollBuilder.ScrollRecord;

/**
 *
 * @author Adam Whittaker
 */
public class SmiteScroll extends LocationSpecificScroll{

    public SmiteScroll(ScrollRecord sp){
        super("Scroll of Smite", "Even the most mute of wizards can bring about magical destruction with this battle scroll.", sp);
    }

    @Override
    public boolean use(Creature c, int x, int y){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
