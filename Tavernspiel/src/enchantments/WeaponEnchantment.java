
package enchantments;

import creatureLogic.Attack.AttackType;
import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * An enchantment that can be affixed to a weapon.
 */
public abstract class WeaponEnchantment extends Enchantment{
    
    public final AttackType attackType;
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The distribution.
     * @param l The level.
     */
    public WeaponEnchantment(String s, Description desc, Distribution d, double l){
        super(s, desc, d, l);
        attackType = null;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The distribution.
     * @param l The level.
     * @param t The associated AttackType.
     */
    public WeaponEnchantment(String s, Description desc, Distribution d, double l, AttackType t){
        super(s, desc, d, l);
        attackType = t;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The distribution.
     * @param l The level.
     * @param aff The affinity of this Enchantment.
     */
    public WeaponEnchantment(String s, Description desc, Distribution d, double l, EnchantmentAffinity aff){
        super(s, desc, d, l, aff);
        attackType = null;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The distribution.
     * @param l The level.
     * @param t The associated AttackType.
     * @param aff The affinity of this Enchantment.
     */
    public WeaponEnchantment(String s, Description desc, Distribution d, double l, AttackType t, EnchantmentAffinity aff){
        super(s, desc, d, l, aff);
        attackType = t;
    }
    
}