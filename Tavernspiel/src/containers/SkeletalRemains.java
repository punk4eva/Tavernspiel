
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class SkeletalRemains extends Chest{
    
    public SkeletalRemains(Item item, int x, int y){
        super(item, x, y);
        description = "A pile of bones from an unlucky adventurer or resident "
                + "of this place. May be worth checking for valuables.";
    }
    
    public SkeletalRemains(Item item, int x, int y, int id){
        super(item, x, y, id);
        description = "A pile of bones from an unlucky adventurer or resident "
                + "of this place. May be worth checking for valuables.";
    }
    
    @Override
    public void open(){
        super.open();
        //1 in 5 chance of wraith spawn.
    }
    
}
