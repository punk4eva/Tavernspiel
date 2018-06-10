
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class EarthquakeScroll extends Scroll{

    public EarthquakeScroll(Image i, boolean idd){
        super("Scroll of Earthquake", "Sends a shockwave strong enough to create a localised earthquake.|Used commonly by ground troops during wars to demolish underground tunnels and trenches.", new ImageIcon(i), idd);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
