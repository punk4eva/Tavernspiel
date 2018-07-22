
package items.equipment;

import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Location.WeaponEntry;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Melee weapon.
 */
public class MeleeWeapon extends HeldWeapon{
    
    public final double accuracy;
    public final double speed;
    public final int reach;
    public final double damageBlock;
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The description.
     * @param lo The image.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     * @param ac
     * @param sp
     * @param re
     * @param db
     */
    public MeleeWeapon(String s, String desc, Supplier<ImageIcon> lo, int dur, Distribution d, int st, double ac, double sp, int re, double db){
        super(s, desc, lo, dur, d, st);
        accuracy = ac;
        speed = sp;
        reach = re;
        damageBlock = db;
        actions = standardActions(3, this);
    }
    
    public MeleeWeapon(WeaponEntry entry){
        super(entry);
        accuracy = entry.ac;
        speed = entry.sp;
        reach = entry.re;
        damageBlock = entry.bl;
        actions = standardActions(3, this);
    }
    
}
