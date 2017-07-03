
package items.equipment;

import items.Apparatus;
import items.Item;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Boots extends Apparatus{
    
    public Boots(String s, ImageIcon i, int dur, Distribution d, int st){
        super(s, i, dur, d, st);
    }
    
    public Boots(Item i, ImageIcon ic, int dur, Distribution d, int st){
        super(i.name, ic, dur, d, st);
    }
    
}
