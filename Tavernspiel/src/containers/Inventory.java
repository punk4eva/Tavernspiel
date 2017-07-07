
package containers;

import items.Item;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class Inventory extends Receptacle{
    
    public Inventory(){
        super(30, "ERROR: You shouldn't be reading this.");
    }
    
    public Inventory(ArrayList<Item> i){
        super(i, 30, "ERROR: You shouldn't be reading this.");
    }
    
}
