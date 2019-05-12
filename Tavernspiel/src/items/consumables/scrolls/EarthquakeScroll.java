
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.builders.ScrollBuilder.ScrollRecord;

/**
 *
 * @author Adam Whittaker
 */
public class EarthquakeScroll extends Scroll{

    public EarthquakeScroll(ScrollRecord sp){
        super("Scroll of Earthquake", "Sends a shockwave strong enough to create a localised earthquake.|Used commonly by ground troops during wars to demolish underground tunnels and trenches.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
