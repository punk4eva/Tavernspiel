
package buffs;

import creatureLogic.AttributeModifier;
import creatures.Creature;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds buffs.
 */
public class BuffBuilder{
    
    public static Buff enraged(Creature c){
        AttributeModifier am = new AttributeModifier();
        am.attackSpeedMultiplier = 1.0 + (1.0/c.attributes.hp);
        am.attackMultiplier = 1.0 + (1.0/c.attributes.hp);
        return new Buff("enraged", am);
    }
    
    public static Buff beserk(){
        AttributeModifier am = new AttributeModifier();
        am.attackSpeedMultiplier = 2;
        am.attackMultiplier = 2;
        return new Buff("beserk", am);
    }
    
    public static Buff shadowmelded(){
        AttributeModifier am = new AttributeModifier();
        am.regenSpeedMultiplier = 1.1;
        return new Buff("shadowmelded", am);
    }
    
}
