
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
public class UpgradeScroll extends Scroll{

    public UpgradeScroll(Image i, boolean idd){
        super("Scroll of Upgrade", "The spell on this scroll will instantly repair minor cracks on gear and increase it's effectiveness.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c, Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
