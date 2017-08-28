
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class IdentityScroll extends Scroll{

    public IdentityScroll(Image i, boolean idd){
        super("Scroll of Identity", "The curious charm of this scroll increases the intelligence of the reader, revealing the secrets of a single item of their choice to them.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
