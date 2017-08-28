
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class SmiteScroll extends Scroll{

    public SmiteScroll(ImageIcon i, boolean idd){
        super("Scroll of Smite", "Even the most mute of wizards can bring about magical destruction with this battle scroll.", i, idd);
    }

    @Override
    public void use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
