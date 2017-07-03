
package items.equipment;

import items.Apparatus;
import items.Item;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Helmet extends Apparatus{
    
    public Helmet(String s, int dur, Distribution d, int st){
        super(s, dur, d, st);
    }
    
    public Helmet(Item i, int dur, Distribution d, int st){
        super(i.name, dur, d, st);
    }
    
}
