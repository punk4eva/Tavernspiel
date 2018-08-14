
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.consumables.ScrollProfile;

/**
 *
 * @author Adam Whittaker
 */
public class UpgradeScroll extends Scroll{

    public UpgradeScroll(ScrollProfile sp){
        super("Scroll of Upgrade", "The spell on this scroll will instantly repair minor cracks on gear and increase it's effectiveness.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
