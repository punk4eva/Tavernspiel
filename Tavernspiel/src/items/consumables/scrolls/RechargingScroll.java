
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class RechargingScroll extends Scroll{

    public RechargingScroll(Image i, boolean idd){
        super("Scroll of Recharging", "A hyaili capacitor is embedded in this scroll which will release hyaili slowly when the scroll is read.", new ImageIcon(i), idd);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
