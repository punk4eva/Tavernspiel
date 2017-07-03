
package items.equipment;

import items.Apparatus;
import items.Item;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Chestplate extends Apparatus{
    
    public Chestplate(String s, ImageIcon ic, int dur, Distribution d, int st){
        super(s, ic, dur, d, st);
    }
    
    public Chestplate(Item i, ImageIcon ic, int dur, Distribution d, int st){
        super(i.name, ic, dur, d, st);
    }
    
}
