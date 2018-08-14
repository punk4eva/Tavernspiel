
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.consumables.ScrollProfile;

/**
 *
 * @author Adam Whittaker
 */
public class CurseScroll extends Scroll{

    public CurseScroll(ScrollProfile sp){
        super("Scroll of Curse", "When activated, this scroll unleashes a wave of malevolent magic that curses items and creatures alike.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
