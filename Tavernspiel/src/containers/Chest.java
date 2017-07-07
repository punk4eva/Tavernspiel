
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class Chest extends Receptacle{
    
    public Chest(Item item){
        super(1, "You won't know what's inside until you open it!");
        items.add(item);
    }
    
}
