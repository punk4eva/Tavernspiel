
package enchantments;

import creatureLogic.Description;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class EnchantmentOfBleeding extends WeaponEnchantment{
    
    public EnchantmentOfBleeding(double level){
        super("Bleeding", new Description("enchantments", "The curse on this weapon makes blood which it touches thinner, allowing it to flow freely."),
                new Distribution(1.0, 10.0-(level*10.0)), level);
    }

    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }
    
}
