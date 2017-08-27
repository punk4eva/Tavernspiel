
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
    
    public Boots(String s, String desc, ImageIcon i, int dur, Distribution d, int st){
        super(s, desc, i, dur, d, st);
    }
    
    public Boots(Item i, int dur, Distribution d, int st){
        super(i.name, i.description, new ImageIcon(i.icon), dur, d, st);
    }
    
}
