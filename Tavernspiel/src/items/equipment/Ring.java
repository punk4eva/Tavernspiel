
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
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param lo
     * @param dur The durability.
     * @param a The action distribution.
     * @param g The Enchantment.
     */
    public Ring(String n, String desc, Supplier<ImageIcon> lo, int dur, Distribution a, Enchantment g){
        super(n, desc, lo, dur, a);
        enchantment = g;
        description.type = "amulets";
    }
    
    /**
     * Creates a new instance.
     * @param rp The RingProfile to copy from.
     */
    public Ring(RingProfile rp){
        super(rp.getName(), rp.getDescription(), rp.getSupplier(), rp.durability, rp.distribution);
        enchantment = rp.glyph;
    }
    
}
