
package items.consumables.scrolls;

import creatures.Creature;
import items.consumables.Scroll;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class SkeletonScroll extends Scroll{

    public SkeletonScroll(Image i, boolean idd){
        super("Scroll of Skeleton", "Since skeletons have been gathering hyaili for a since their deaths, they can be easily summoned to obey one's will. This scroll summons a nearby one.||Loraz the Sage of Nature used a spell like the one on this parchment to raise an army af corpses (including dunadans) to wipe out the rest of the dunadan population.", new ImageIcon(i), idd);
    }

    @Override
    public void use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}