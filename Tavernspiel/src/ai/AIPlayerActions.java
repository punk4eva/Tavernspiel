
package ai;

import creatureLogic.Attack;
import creatureLogic.EnClass;
import creatures.Creature;
import creatures.Hero;
import listeners.StepListener;
import logic.Distribution;
import tiles.Door;

/**
 *
 * @author Adam Whittaker
 * 
 * AIBaseActions but tailored for the Hero.
 */
public class AIPlayerActions extends AIBaseActions{
    
    public static calcDexterity dexterityCalculation = c -> ((Hero) c).job==EnClass.Rogue ? 
            c.attributes.dexterity / Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 
            c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);
    @Override
    public void resetDexterityCalculation(){accuracyCalculation = c -> ((Hero) c).job==EnClass.Rogue ? 
            c.attributes.dexterity / Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 
            c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);}
    
    @Override
    public void attack(Attack attack, Creature attacked){
        if(successfulHit(attack, attacked)){
            attacked.getAttacked(attack); 
        }
    }
    
    /**
     * Tests whether a hit was successful.
     * @param attack
     * @param attacked
     * @return True if it was, false if not.
     */
    public static boolean successfulHit(Attack attack, Hero attacked){
        double attackedDexterity = dexterityCalculation.calc(attacked);
        return Distribution.randomDouble(0, attack.accuracy) >=
                Distribution.randomDouble(0, attackedDexterity);
    }
    
    public void smootheRaw(Hero h, int x, int y){
        if(!h.animator.currentName.equals("move")) h.changeAnimation("move");
        h.smootheXY(x, y);
        if(h.attributes.ai.destinationx==h.x&&h.attributes.ai.destinationy==h.y){
            h.changeAnimation("stand");
        }
    }
    
}
