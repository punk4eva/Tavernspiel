
package buffs;

import creatureLogic.AttributeModifier;
import creatures.Creature;

/**
 *
 * @author Adam Whittaker
 */
public class BuffBuilder{
    
    /**
     * @unfinished
     */
    public static Buff getBuff(String str, Creature c){
        AttributeModifier am = new AttributeModifier();
        switch(str){
            case "beserk":
                am.attackSpeedMultiplier = 2;
                am.attackMultiplier = 2;
                return new Buff(str, am);
            case "enraged":
                am.attackSpeedMultiplier = 1.0 + (1.0/c.attributes.hp);
                am.attackMultiplier = 1.0 + (1.0/c.attributes.hp);
                return new Buff(str, am);
            default:
                return new Buff(str);
        }
    }
    
}
