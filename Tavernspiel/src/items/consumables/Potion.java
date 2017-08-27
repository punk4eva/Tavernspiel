
package items.consumables;

import items.Consumable;
import items.ItemAction;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Potion extends Consumable{
    
    public Potion(String n, String desc, ImageIcon i){
        super(n, desc, i);
        actions[2] = new ItemAction("DRINK");
    }
    
    public Potion(String n, String desc, ImageIcon i, int q){
        super(n, desc, i, q);
        actions[2] = new ItemAction("DRINK");
    }
    
}
