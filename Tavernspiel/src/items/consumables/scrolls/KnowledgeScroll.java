
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import items.consumables.ScrollProfile;

/**
 *
 * @author Adam Whittaker
 */
public class KnowledgeScroll extends Scroll{

    public KnowledgeScroll(ScrollProfile sp){
        super("Scroll of Knowledge", "This scroll chisels an imprint of the reader's surroundings into their mind, revealling hidden doors and traps.", sp);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
