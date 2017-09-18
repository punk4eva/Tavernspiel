
package enchantments;

import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class EnchantmentOfShock extends WeaponEnchantment{
    
    public EnchantmentOfShock(double level){
        super("Shock", new Description("enchantments", "The enchantment sends electric sparks through the opponent's body."),
                new Distribution(1, 10.0-(level*10.0)), level);
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }
    
}
