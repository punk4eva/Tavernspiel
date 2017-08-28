
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class InfiniteHorseScroll extends Scroll{

    public InfiniteHorseScroll(Image i, boolean idd){
        super("Scroll of Infinite Horse", "The rare summoning magic on this parchment will summon a majestic beast which can be used to traverse large distances in a short period of time.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
