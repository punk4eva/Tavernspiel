
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class AnimationScroll extends Scroll{

    public AnimationScroll(Image i, boolean idd){
        super("Scroll of Animation", "When in need of urgent aid in battle one must read this scroll and all their problems shall be solved!|Use multiple scrolls close to the summon to empower it.|If the monster grows to a certain size, it can be possessed and can grow exponentially larger with the hyaili that it generates.", new ImageIcon(i), idd);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
