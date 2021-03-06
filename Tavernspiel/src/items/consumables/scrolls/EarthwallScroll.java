
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.builders.ScrollBuilder.ScrollRecord;

/**
 *
 * @author Adam Whittaker
 */
public class EarthwallScroll extends Scroll{

    public EarthwallScroll(ScrollRecord sp){
        super("Scroll of Earthwall", "This nifty parchment commands the ground to rise to form a temporary wall, thus providing refuge from a perilous situation.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
