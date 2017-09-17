
package enchantments;

import creatureLogic.Attack.AttackType;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * An enchantment that can be affixed to a weapon.
 */
public class WeaponEnchantment extends Enchantment{
    
    public final AttackType attackType;
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param d The distribution.
     * @param l The level.
     */
    public WeaponEnchantment(String s, Distribution d, double l){
        super(s, d, l);
        attackType = null;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param d The distribution.
     * @param l The level.
     * @param t The associated AttackType.
     */
    public WeaponEnchantment(String s, Distribution d, double l, AttackType t){
        super(s, d, l);
        attackType = t;
    }
    
}
