
package enchantments;

import buffs.BuffBuilder;
import creatureLogic.Attack;
import creatureLogic.Description;
import creatures.Creature;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public final class EnchantmentOfBlindness extends WeaponEnchantment{
    
    public EnchantmentOfBlindness(double level){
        super("Blindness", new Description("enchantments", "The spell releases a flash of light which temporarily blinds enemies."),
                new Distribution(1, 10.0-(level*10.0)), level);
        hueR1=173;
        hueG1=132;
        hueB1=27;
        hueR2=224;
        hueG2=189;
        hueB2=15;
    }
    
    @Override
    public void update(int lev){
        level = lev;
        action = new Distribution(1.0, 10.0-(level*10.0));
    }

    @Override
    public void onHit(Creature victim, Attack attack){
        if(shouldActivate()){
            victim.addBuff(BuffBuilder.blindness(27*level*level+3));
        }
    }
    
}
