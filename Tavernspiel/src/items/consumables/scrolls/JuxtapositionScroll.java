
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class JuxtapositionScroll extends Scroll{

    public JuxtapositionScroll(Image i, boolean idd){
        super("Scroll of Juxtaposition", "This mysterious parchment possesses the power to move the reader to a nearby location.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
