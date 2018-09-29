
package items.equipment;

import creatureLogic.Description;
import items.Apparatus;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a weapon that is equippable.
 */
public abstract class HeldWeapon extends Apparatus{
    
    private final static long serialVersionUID = 1231141312459L;
    
    public Distribution damageDistrib;
    
    public HeldWeapon(String s, Description desc, Supplier<ImageIcon> lo, int dur, Distribution d, int st){
        super(s, desc, lo, dur, st);
        damageDistrib = d;
    }
    
}
