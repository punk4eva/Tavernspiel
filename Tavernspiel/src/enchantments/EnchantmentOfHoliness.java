
package enchantments;

import creatureLogic.Attack.AttackType;
import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * Adds some more Holy damage on top of an attack.
 */
public final class EnchantmentOfHoliness extends WeaponEnchantment{
    
    /**
     * Creates a new instance.
     * @param level The level.
     */
    public EnchantmentOfHoliness(double level){
        super("Holiness", new Description("enchantments", "The enchantment blesses the weapon with extra holy damage."),
                new Distribution(0, (int)(20.0*level)),
                level, AttackType.HOLY, EnchantmentAffinity.HEALING);
        hueR1=190;
        hueG1=190;
        hueB1=190;
        hueR2=237;
        hueG2=237;
        hueB2=237;
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(0, (int)(level*20.0));
    }
    
}
