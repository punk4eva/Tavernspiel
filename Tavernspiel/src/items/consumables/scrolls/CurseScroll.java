
package items.consumables.scrolls;

import creatures.Creature;
import items.builders.ScrollBuilder.ScrollRecord;
import items.consumables.Scroll;

/**
 *
 * @author Adam Whittaker
 */
public class CurseScroll extends Scroll{

    public CurseScroll(ScrollRecord sp){
        super("Scroll of Curse", "When activated, this scroll unleashes a wave of malevolent magic that curses items and creatures alike.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
