
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
public class CountercurseScroll extends Scroll{

    public CountercurseScroll(Image i, boolean idd){
        super("Scroll of Countercurse", "The strong unbinding magic stored in this parchment can dispell malevolant effects and cleanse all except the most powerfull curses on items or creatures.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c, Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
