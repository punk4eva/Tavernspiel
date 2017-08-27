
package items.consumables;

import creatures.Creature;
import javax.swing.ImageIcon;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class WonderScroll extends Scroll{
    
    public WonderScroll(ImageIcon i){
        super("Scroll of Wonder", "This ancient scroll's mark has faded beyond recongnition over the years although you think you can still salvage the apparently positive effect that it had.", i);
    }

    @Override
    public void use(Creature c, Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
