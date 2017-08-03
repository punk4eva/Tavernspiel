
package items.consumables;

import items.Consumable;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Potion extends Consumable{
    
    public Potion(String n, ImageIcon i){
        super(n, i);
    }
    
    public Potion(String n, ImageIcon i, int q){
        super(n, i, q);
    }
    
}
