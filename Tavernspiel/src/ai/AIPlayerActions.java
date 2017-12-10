
package ai;

import creatureLogic.Attack;
import creatureLogic.EnClass;
import creatures.Creature;
import creatures.Hero;
import exceptions.ReceptacleOverflowException;
import gui.MainClass;
import gui.Window;
import items.Item;
import logic.Distribution;
import logic.Utils.Unfinished;

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
    
    /**
     * Picks up an Item from the floor.
     * @param c The Creature.
     * @throws NullPointerException if there is no Receptacle.
     */
    @Override
    @Unfinished("Add 'pickup' sound effect")
    public void pickUp(Creature c){
        Item i = c.area.pickUp(c.x, c.y);
        try{
            c.inventory.push(i);
            Window.main.soundSystem.playSFX("pickUp.wav");
        }catch(ReceptacleOverflowException e){
            c.area.plop(i, c.x, c.y);
            MainClass.messageQueue.add("red", "Your pack is too full for the " +
                    i.toString(3));
        }
    }
    
}
