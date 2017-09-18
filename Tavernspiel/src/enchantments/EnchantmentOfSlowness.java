
package enchantments;

import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class EnchantmentOfSlowness extends WeaponEnchantment{
    
    public EnchantmentOfSlowness(double level){
        super("Slowness", new Description("enchantments", "This weapon increases the stress of movement for it's enemies."),
                new Distribution(1, 10.0-(level*10.0)), level);
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }
    
}
