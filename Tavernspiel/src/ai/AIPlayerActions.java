
package ai;

import creatures.Creature;
import creatures.Hero;
import creatures.Hero.EnClass;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class AIPlayerActions extends AIBaseActions{
    
    public static calcDexterity dexterityCalculation = c -> ((Hero) c).job==EnClass.Rogue ? 
            c.attributes.dexterity / Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 
            c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);
    @Override
    public void resetDexterityCalculation(){accuracyCalculation = c -> ((Hero) c).job==EnClass.Rogue ? 
            c.attributes.dexterity / Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 
            c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);}
    
    public void attack(Hero attacker, Creature attacked){
        if(successfulHit(attacker, attacked)){
            attacked.getAttacked(attacker, attacker.nextHit()); 
        }
    }
    
    public void attack(Creature attacker, Creature attacked){
        if(successfulHit(attacker, attacked)){
            attacked.getAttacked(attacker, attacker.nextHit()); 
        }
    }
    
    public static boolean successfulHit(Creature attacker, Hero attacked){
        double attackerAccuracy = accuracyCalculation.calc(attacker);
        double attackedDexterity = dexterityCalculation.calc(attacked);
        return Distribution.randomDouble(0, attackerAccuracy) >=
                Distribution.randomDouble(0, attackedDexterity);
    }
    
}
