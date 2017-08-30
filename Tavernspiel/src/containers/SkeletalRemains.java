
package containers;

import creatureLogic.Description;
import items.Item;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class SkeletalRemains extends Chest{
    
    /**
     * Creates a new instance.
     * @param item 
     * @param x
     * @param y
     */
    public SkeletalRemains(Item item, int x, int y){
        super(item, x, y);
        description = new Description("receptacle", "A pile of bones from an unlucky adventurer or resident "
                + "of this place. May be worth checking for valuables.");
    }
    
    @Override
    public void open(Area area){
        super.open(area);
        //1 in 5 chance of wraith spawn.
    }
    
}
