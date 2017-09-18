
package enchantments;

import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class EnchantmentOfBurning extends WeaponEnchantment{
    
    public EnchantmentOfBurning(double level){
        super("Burning", new Description("enchantments", "The hex sets foes ablaze."),
                new Distribution(1, 10.0-(level*10.0)), level);
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }
    
}
