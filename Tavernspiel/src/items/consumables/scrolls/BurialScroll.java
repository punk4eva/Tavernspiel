
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.LocationSpecificScroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class BurialScroll extends LocationSpecificScroll{

    public BurialScroll(Image i, boolean idd){
        super("Scroll of Burial", "Buries everything in the effect area in the ground.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c, int x, int y){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
