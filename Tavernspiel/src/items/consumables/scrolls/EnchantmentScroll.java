
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
public class EnchantmentScroll extends Scroll{

    public EnchantmentScroll(Image i, boolean idd){
        super("Scroll of Enchantment", "There is a potent enchantment on this parchment which, when read out loud, will be reaffixed to a peace of equipment of the reader's choice.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c, Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
