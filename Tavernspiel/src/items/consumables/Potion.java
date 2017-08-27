
package items.consumables;

import items.Consumable;
import items.ItemAction;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Potion extends Consumable{
    
    public Potion(String n, String desc, ImageIcon i, boolean idd){
        super(n, desc, i, idd);
        actions[2] = new ItemAction("DRINK");
    }
    
    public Potion(String n, String desc, ImageIcon i, boolean idd, int q){
        super(n, desc, i, idd, q);
        actions[2] = new ItemAction("DRINK");
    }
    
}
