
package items;

import creatureLogic.Description;
import creatures.Creature;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Consumable extends Item{
    
    public Consumable(String n, String desc, ImageIcon i, boolean idd){
        super(n, desc, i, true);
        identified = idd;
        actions = ItemAction.getArray(3);
    }
    
    public Consumable(String n, String desc, ImageIcon i, boolean idd, int q){
        super(n, desc, i, q);
        identified = idd;
        actions = ItemAction.getArray(3);
    }
    
    public Consumable(String n, String desc, ImageIcon i, boolean idd, int q, boolean flam){
        super(n, desc, i, q, flam);
        identified = idd;
        actions = ItemAction.getArray(3);
    }
    
    public Consumable(String n, Description desc, Image im, boolean idd){
        super(n, desc, im, 1);
        identified = idd;
        actions = ItemAction.getArray(3);
    }
    
    public abstract void use(Creature c);
    
}
