
package items.consumables;

import items.Consumable;
import items.ItemAction;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 * 
 * This class models Scrolls.
 */
public abstract class Scroll extends Consumable{
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The name of the Item.
     * @param i The name of the Item.
     * @param idd Whether the Consumable is identified.
     */
    public Scroll(String n, String desc, ImageIcon i, boolean idd){
        super(n, desc, i, idd, 1, true);
        actions[2] = new ItemAction("READ");
        description.type = "scrolls";
    }
    
    /**
     * Creates a new instance.
     * @param n The name of the Item.
     * @param desc The name of the Item.
     * @param i The name of the Item.
     * @param idd Whether the Consumable is identified.
     * @param quantity The quantity of this Item.
     */
    public Scroll(String n, String desc, ImageIcon i, boolean idd, int quantity){
        super(n, desc, i, idd, quantity, true);
        actions[2] = new ItemAction("READ");
        description.type = "scrolls";
    }
    
}
