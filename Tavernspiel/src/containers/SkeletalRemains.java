
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class SkeletalRemains extends Chest{
    
    public SkeletalRemains(Item item, int x, int y){
        super(item, x, y);
    }
    
    @Override
    public void open(){
        super.open();
        //1 in 5 chance of wraith spawn.
    }
    
}
