
package containers;

import creatures.Creature;
import items.Item;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Area;
import level.Location;

/**
 *
 * @author Adam Whittaker
 */
public class SkeletalRemains extends Chest{
    
    private final static long serialVersionUID = 2049310321222227L;
    
    /**
     * Creates a new instance.
     * @param loc
     * @param item 
     * @param x
     * @param y
     */
    public SkeletalRemains(String loc, Item item, int x, int y){
        super((Serializable & Supplier<ImageIcon>)
                () -> Location.getSkeletalRemainsIcon(loc), "A pile of bones from"
                        + " an unlucky adventurer or resident of this place. "
                        + "May be worth checking for valuables.", item, x, y);
    }
    
    @Override
    public void interact(Creature c, Area area){
        super.interact(c, area);
        //@Unfinished 1 in 5 chance of wraith spawn.
    }
    
}
