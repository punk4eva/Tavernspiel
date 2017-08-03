
package items.consumables;

import items.Consumable;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Scroll extends Consumable{
    
    public Scroll(String n, ImageIcon i){
        super(n, i, 1, true);
    }
    
    public Scroll(String n, ImageIcon i, int q){
        super(n, i, q, true);
    }
    
}
