
package items.equipment;

import level.Location.WeaponEntry;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Melee weapon.
 */
public class MeleeWeapon extends HeldWeapon{
    
    private final static long serialVersionUID = 222324352203459L;
    
    public final double accuracy;
    public final double speed;
    public final int reach;
    public final double damageBlock;
    
    public MeleeWeapon(WeaponEntry entry){
        super(entry);
        accuracy = entry.ac;
        speed = entry.sp;
        reach = entry.re;
        damageBlock = entry.bl;
        actions = standardActions(3, this);
    }
    
}
