
package containers;

import items.Item;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class Mimic extends Chest{
    
    public Mimic(Item item, int x, int y){
        super(item, x, y);
    }
    
    @Override
    public void open(Area area){
        throw new UnsupportedOperationException("Unfinished.");
        //Spawn mimic creature.
        //Destroy mimic chest.
    }
    
}
