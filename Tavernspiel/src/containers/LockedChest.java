
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class LockedChest extends Chest{
    
    public LockedChest(Item item, int x, int y){
        super(item, x, y);
        description = "You can't open this chest without a key.";
    }
    
}
