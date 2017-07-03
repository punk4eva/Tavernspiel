
package containers;

import items.Item;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class Inventory extends Receptacle{
    
    public Inventory(){
        super(30);
    }
    
    public Inventory(ArrayList<Item> i){
        super(i, 7);
    }
    
}
