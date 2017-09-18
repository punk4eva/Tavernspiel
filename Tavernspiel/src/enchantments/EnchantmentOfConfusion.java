
package enchantments;

import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class EnchantmentOfConfusion extends WeaponEnchantment{
    
    public EnchantmentOfConfusion(double level){
        super("Confusion", new Description("enchantments", "The rogueish enchantment affects the internal balance of an attacker, reducing their ability"
                + "to walk, allowing an easy escape."),
                new Distribution(1, 10.0-(level*10.0)), level);
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }
    
}
