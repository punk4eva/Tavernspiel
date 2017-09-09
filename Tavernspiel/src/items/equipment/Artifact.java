
package items.equipment;

import buffs.Buff;
import items.Apparatus;
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
     * @param i The image.
     * @param dur The durability.
     * @param a The action distribution.
     * @param b The buff.
     * @param charge The charge.
     */
    public Artifact(String n, String desc, ImageIcon i, int dur, Distribution a, Buff b, int charge){
        super(n, desc, i, dur, a);
        description.type = "amulets";
    }
    
}
