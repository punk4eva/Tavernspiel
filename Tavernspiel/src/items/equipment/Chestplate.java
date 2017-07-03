
package items.equipment;

import items.Apparatus;
import items.Item;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Chestplate extends Apparatus{
    
    public Chestplate(String s, int dur, Distribution d, int st){
        super(s, dur, d, st);
    }
    
    public Chestplate(Item i, int dur, Distribution d, int st){
        super(i.name, dur, d, st);
    }
    
}
