
package items.equipment;

import items.Apparatus;
import items.Item;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Leggings extends Apparatus{
    
    public Leggings(String s, String desc, ImageIcon ic, int dur, Distribution d, int st){
        super(s, desc, ic, dur, d, st);
    }
    
    public Leggings(Item i, ImageIcon ic, int dur, Distribution d, int st){
        super(i.name, i.description, new ImageIcon(i.icon), dur, d, st);
    }
    
}
