
package items;

import creatures.Creature;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Consumable extends Item{
    
    public Consumable(String n, ImageIcon i){
        super(n, i, true);
        actions = ItemAction.getArray(3);
    }
    
    public Consumable(String n, ImageIcon i, int q){
        super(n, i, q);
        actions = ItemAction.getArray(3);
    }
    
    public Consumable(String n, ImageIcon i, int q, boolean flam){
        super(n, i, q, flam);
        actions = ItemAction.getArray(3);
    }
    
    public abstract void use(Creature c);
    
}
