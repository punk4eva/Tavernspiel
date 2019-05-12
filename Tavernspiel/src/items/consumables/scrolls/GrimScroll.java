
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.LocationSpecificScroll;
import items.builders.ScrollBuilder.ScrollRecord;

/**
 *
 * @author Adam Whittaker
 */
public class GrimScroll extends LocationSpecificScroll{

    public GrimScroll(ScrollRecord sp){
        super("Scroll of the Grim", "The horrible text on this sheet of paper aggrevates the spirits of this place when read aloud, and they drag a creature of the reader's choosing with them to the underworld.", sp);
    }

    @Override
    public boolean use(Creature c, int x, int y){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
