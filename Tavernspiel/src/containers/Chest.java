
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class Chest extends Receptacle{
    
    public Chest(Item item){
        super(1);
        items.add(item);
    }
    
}
