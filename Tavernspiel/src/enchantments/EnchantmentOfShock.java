
package enchantments;

import creatureLogic.Attack;
import creatureLogic.Description;
import creatures.Creature;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public final class EnchantmentOfShock extends WeaponEnchantment{
    
    public EnchantmentOfShock(double level){
        super("Shock", new Description("enchantments", "The enchantment sends electric sparks through the opponent's body."),
                new Distribution(1, 10.0-(level*10.0)), level);
        hueR2=13;
        hueG2=166;
        hueB2=236;
        hueR1=24;
        hueG1=80;
        hueB1=182;
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }

    @Override
    public void onHit(Creature victim, Attack attack){
        if(shouldActivate()){
            throw new UnsupportedOperationException();
        }
    }
    
}
