
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.consumables.ScrollProfile;

/**
 *
 * @author Adam Whittaker
 */
public class RechargingScroll extends Scroll{

    public RechargingScroll(ScrollProfile sp){
        super("Scroll of Recharging", "A hyaili capacitor is embedded in this scroll which will release hyaili slowly when the scroll is read.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
