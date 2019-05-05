
package enchantments;

import creatureLogic.Attack;
import creatureLogic.Attack.AttackType;
import creatureLogic.Description;
import creatures.Creature;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * An enchantment that can be affixed to a weapon.
 */
public abstract class WeaponEnchantment extends Enchantment{
    
    public final AttackType attackType;
    private final static Distribution distrib = new Distribution(new int[]{10, 5, 7, 5, 1, 13, 13, 10, 10});
    
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
    
    /**
     * Returns a random WeaponEnchantment.
     * @return
     */
    public static WeaponEnchantment getRandomEnchantment(){
        switch((int)distrib.next()){
            case 0: return new EnchantmentOfBleeding(getLevel());
            case 1: return new EnchantmentOfBlindness(getLevel());
            case 2: return new EnchantmentOfBurning(getLevel());
            case 3: return new EnchantmentOfConfusion(getLevel());
            case 4: return new EnchantmentOfExcess(getLevel());
            case 5: return new EnchantmentOfHoliness(getLevel());
            case 6: return new EnchantmentOfUnholiness(getLevel());
            case 7: return new EnchantmentOfShock(getLevel());
            case 8: return new EnchantmentOfSlowness(getLevel());
        }
        throw new IllegalStateException("WeaponEnchantment.distrib has failed.");
    }
    
    private static double getLevel(){
        return 1.0/(1.9*(0.38+Distribution.R.nextDouble())) - 0.38;
    }
    
    /**
     * Determines what happens when the weapon is used.
     * @param victim
     * @param attack
     */
    public abstract void onHit(Creature victim, Attack attack);
    
}
