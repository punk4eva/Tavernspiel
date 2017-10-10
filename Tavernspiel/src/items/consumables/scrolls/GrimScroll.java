
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.LocationSpecificScroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class GrimScroll extends LocationSpecificScroll{

    public GrimScroll(Image i, boolean idd){
        super("Scroll of the Grim", "The horrible text on this sheet of paper aggrevates the spirits of this place when read aloud, and they drag a creature of the reader's choosing with them to the underworld.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c, int x, int y){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
