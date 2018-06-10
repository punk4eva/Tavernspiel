
package items.consumables.scrolls;

import creatures.Creature;
import items.Item;
import items.consumables.ItemSpecificScroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class CountercurseScroll extends ItemSpecificScroll{

    public CountercurseScroll(Image i, boolean idd){
        super("Scroll of Countercurse", "The strong unbinding magic stored in this parchment can dispell malevolant effects and cleanse all except the most powerfull curses on items or creatures.", new ImageIcon(i), idd);
    }

    @Override
    public boolean use(Creature c, Item i){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
