
package items.equipment;

import enchantments.Enchantment;
import items.Apparatus;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a ring.
 */
public class Ring extends Apparatus{
    
    private final static long serialVersionUID = 58843820946389L;
    
    /**
     * Creates a new instance.
     * @param name The name.
     * @param dur The durability.
     * @param a The action distribution.
     * @param g The Enchantment.
     * @param rp The RingProfile.
     */
    public Ring(String name, int dur, Distribution a, Enchantment g, RingProfile rp){
        super(name, rp.description, rp.loader, dur, a);
        enchantment = g;
        description.type = "amulets";
    }
    
}
