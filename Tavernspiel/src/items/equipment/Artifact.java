
package items.equipment;

import buffs.Buff;
import items.Apparatus;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents Artifacts.
 */
public class Artifact extends Apparatus{
    
    public Buff buff;
    public int charges;
    public int maxCharges;
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param lo
     * @param dur The durability.
     * @param a The action distribution.
     * @param b The buff.
     * @param charge The charge.
     */
    public Artifact(String n, String desc, Supplier<ImageIcon> lo, int dur, Distribution a, Buff b, int charge){
        super(n, desc, lo, dur, a);
        description.type = "amulets";
    }
    
}
