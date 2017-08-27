
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import java.awt.Image;
import javax.swing.ImageIcon;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class KnowledgeScroll extends Scroll{

    public KnowledgeScroll(Image i, boolean idd){
        super("Scroll of Knowledge", "This scroll chisels an imprint of the reader's surroundings into their mind, revealling hidden doors and traps.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c, Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
