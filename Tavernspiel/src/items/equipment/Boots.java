
package items.equipment;

import items.Apparatus;
import items.Item;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents Boots.
 */
public class Boots extends Apparatus{
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The description.
     * @param i The image.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Boots(String s, String desc, ImageIcon i, int dur, Distribution d, int st){
        super(s, desc, i, dur, d, st);
        description.type = "armour";
    }
    
    /**
     * Creates a new instance from an Item.
     * @param i The item to copy.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength requirement.
     */
    public Boots(Item i, int dur, Distribution d, int st){
        super(i.name, i.description, i.animation, dur, d, st);
        description.type = "armour";
    }
    
}
