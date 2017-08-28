
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
    
    public Chestplate(String s, String desc, ImageIcon ic, int dur, Distribution d, int st){
        super(s, desc, ic, dur, d, st);
        description.type = "armour";
    }
    
    public Chestplate(Item i, int dur, Distribution d, int st){
        super(i.name, i.description, new ImageIcon(i.icon), dur, d, st);
        description.type = "armour";
    }
    
}
