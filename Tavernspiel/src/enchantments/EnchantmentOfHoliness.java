
package enchantments;

import creatureLogic.Attack.AttackType;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * Adds some more Holy damage on top of an attack.
 */
public class EnchantmentOfHoliness extends WeaponEnchantment{
    
    /**
     * Creates a new instance.
     * @param level The level.
     */
    public EnchantmentOfHoliness(double level){
        super("Holiness", new Distribution(0, (int)(20*level)),
                level, AttackType.HOLY);
    }
    
}
