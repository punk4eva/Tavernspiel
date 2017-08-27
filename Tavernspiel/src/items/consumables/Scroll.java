
package items.consumables;

import items.Consumable;
import items.ItemAction;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Scroll extends Consumable{
    
    public Scroll(String n, String desc, ImageIcon i, boolean idd){
        super(n, desc, i, idd, 1, true);
        actions[2] = new ItemAction("READ");
    }
    
    public Scroll(String n, String desc, ImageIcon i, boolean idd, int quantity){
        super(n, desc, i, idd, quantity, true);
        actions[2] = new ItemAction("READ");
    }
    
}
