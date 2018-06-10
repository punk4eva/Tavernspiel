
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.LocationSpecificScroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class SmiteScroll extends LocationSpecificScroll{

    public SmiteScroll(Image i, boolean idd){
        super("Scroll of Smite", "Even the most mute of wizards can bring about magical destruction with this battle scroll.", new ImageIcon(i), idd);
    }

    @Override
    public boolean use(Creature c, int x, int y){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
