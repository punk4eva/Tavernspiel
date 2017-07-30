
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class Chest extends Receptacle{
    
    public Chest(Item item, int x, int y){
        super(1, "You won't know what's inside until you open it!", x, y);
        items.add(item);
    }
    
    public void open(){
        throw new UnsupportedOperationException("Unfinished.");
        //Remove chest and put item on ground.
    }
    
}
