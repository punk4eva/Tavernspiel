
package enchantments;

import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class EnchantmentOfDuelling extends WeaponEnchantment{
    
    //actionDistrib is parryChance.
    public Distribution damageReduction;
    
    public EnchantmentOfDuelling(double level){
        super("Duelling", new Description("enchantments", "The enchantment on this weapon reduces it's damage significantly, but exhausts opponents seriously"
                + " with its hits. It allows the wielder to reverse the direction of an attack upon parrying, and has a high parry chance."),
                new Distribution(1, 10.5-(level*10.0)), level);
        damageReduction = new Distribution(0, 1.05-level);
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.5-(level*10.0));
        damageReduction = new Distribution(0, 1.05-level);
    }
    
}
