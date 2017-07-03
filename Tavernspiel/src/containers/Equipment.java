
package containers;

import items.Apparatus;
import items.Helmet;
import items.Item;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class Equipment extends Receptacle{
    
    
    
    public Equipment(){
        super(7);
    }
    
    public Equipment(ArrayList<Apparatus> i){
        super(i, 7);
    }
    
    public Helmet getHelmet(){
        return new Helmet(items.get(3));
    }
    
}
