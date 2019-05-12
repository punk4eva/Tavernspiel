
package items.consumables.scrolls;

import creatures.Creature;
import items.Item;
import items.builders.ScrollBuilder.ScrollRecord;
import items.consumables.ItemSpecificScroll;

/**
 *
 * @author Adam Whittaker
 */
public class CountercurseScroll extends ItemSpecificScroll{

    public CountercurseScroll(ScrollRecord sp){
        super("Scroll of Countercurse", "The strong unbinding magic stored in this parchment can dispell malevolant effects and cleanse all except the most powerfull curses on items or creatures.", sp);
    }

    @Override
    public boolean use(Creature c, Item i){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
