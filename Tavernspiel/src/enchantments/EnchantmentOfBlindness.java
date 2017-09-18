
package enchantments;

import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class EnchantmentOfBlindness extends WeaponEnchantment{
    
    public EnchantmentOfBlindness(double level){
        super("Blindness", new Description("enchantments", "The spell releases a flash of light which temporarily blinds enemies."),
                new Distribution(1, 10.0-(level*10.0)), level);
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }
    
}
