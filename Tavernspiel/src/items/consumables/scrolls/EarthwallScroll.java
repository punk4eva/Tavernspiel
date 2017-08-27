
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
public class EarthwallScroll extends Scroll{

    public EarthwallScroll(Image i, boolean idd){
        super("Scroll of Earthwall", "This nifty parchment commands the ground to rise to form a temporary wall, thus providing refuge from a perilous situation.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c, Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
