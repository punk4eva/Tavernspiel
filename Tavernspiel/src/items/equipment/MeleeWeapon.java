
package items.equipment;

import items.Apparatus;
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
     * @param ic The image.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     * @param ac
     * @param sp
     * @param re
     * @param db
     */
    public MeleeWeapon(String s, String desc, ImageIcon ic, int dur, Distribution d, int st, double ac, double sp, int re, double db){
        super(s, desc, ic, dur, d, st);
        accuracy = ac;
        speed = sp;
        reach = re;
        damageBlock = db;
        actions = Apparatus.standardActions(3, this);
    }
    
    public MeleeWeapon(WeaponEntry entry){
        super(entry);
        accuracy = entry.ac;
        speed = entry.sp;
        reach = entry.re;
        damageBlock = entry.bl;
        actions = Apparatus.standardActions(3, this);
    }
    
}
