
package containers;

import exceptions.ReceptacleOverflowException;
import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class Floor extends Receptacle{
    
    public Floor(){
        super("There is nothing interesting here.");
    }
    
    public Floor(Item i) throws ReceptacleOverflowException{
        super("There is nothing interesting here.");
        push(i);
    }
    
    public Floor(Receptacle r) throws ReceptacleOverflowException{
        super("There is nothing interesting here.");
        pushAll(r);
    }
    
}
