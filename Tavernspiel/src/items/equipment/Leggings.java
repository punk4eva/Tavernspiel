
package items.equipment;

import items.Apparatus;
import items.Item;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Leggings extends Apparatus{
    
    public Leggings(String s, int dur, Distribution d, int st){
        super(s, dur, d, st);
    }
    
    public Leggings(Item i, int dur, Distribution d, int st){
        super(i.name, dur, d, st);
    }
    
}
