
package items;

import creatures.Creature;
import javax.swing.ImageIcon;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Consumable extends Item{
    
    public Consumable(String n, String desc, ImageIcon i){
        super(n, desc, i, true);
        actions = ItemAction.getArray(3);
    }
    
    public Consumable(String n, String desc, ImageIcon i, int q){
        super(n, desc, i, q);
        actions = ItemAction.getArray(3);
    }
    
    public Consumable(String n, String desc, ImageIcon i, int q, boolean flam){
        super(n, desc, i, q, flam);
        actions = ItemAction.getArray(3);
    }
    
    public abstract void use(Creature c, Area area);
    
}
