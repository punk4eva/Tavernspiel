
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class CurseScroll extends Scroll{

    public CurseScroll(Image i, boolean idd){
        super("Scroll of Curse", "When activated, this scroll unleashes a wave of malevolent magic that curses items and creatures alike.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
