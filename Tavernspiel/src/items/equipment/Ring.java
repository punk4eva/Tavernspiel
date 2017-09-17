
package items.equipment;

import enchantments.Enchantment;
import items.Apparatus;
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
     * @param ic The image.
     * @param dur The durability.
     * @param a The action distribution.
     * @param g The Enchantment.
     */
    public Ring(String n, String desc, ImageIcon ic, int dur, Distribution a, Enchantment g){
        super(n, desc, ic, dur, a);
        enchantment = g;
        description.type = "amulets";
    }
    
    /**
     * Creates a new instance.
     * @param rp The RingProfile to copy from.
     */
    public Ring(RingProfile rp){
        super(rp.getName(), rp.getDescription(), rp.getImageIcon(), rp.durability, rp.distribution);
        enchantment = rp.glyph;
    }
    
}
