
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class Mimic extends Chest{
    
    public Mimic(Item item, int x, int y){
        super(item, x, y);
    }
    
    public Mimic(Item item, int x, int y, int id){
        super(item, x, y, id);
    }
    
    @Override
    public void open(){
        throw new UnsupportedOperationException("Unfinished.");
        //Spawn mimic creature.
        //Destroy mimic chest.
    }
    
}
