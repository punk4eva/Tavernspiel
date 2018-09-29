
package items.equipment;

import buffs.Buff;
import creatureLogic.Description;
import items.Apparatus;
import java.util.function.Supplier;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents Artifacts.
 */
public abstract class Artifact extends Apparatus{
    
    private final static long serialVersionUID = 12391223;
    
    private final Buff buff; //@Unfinished
    public int charges;
    public int maxCharges;
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param lo
     * @param dur The durability.
     * @param b The buff.
     * @param charge The charge.
     */
    public Artifact(String n, Description desc, Supplier<ImageIcon> lo, int dur, Buff b, int charge){
        super(n, desc, lo, dur);
        buff = b;
        description.type = "amulets";
    }
    
}
