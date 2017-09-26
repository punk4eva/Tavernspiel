
package ai;

import ai.IntelligentAI1.EnState;
import containers.Floor;
import containers.PurchasableHeap;
import creatureLogic.Attack;
import creatures.Creature;
import creatures.Hero;
import exceptions.ReceptacleOverflowException;
import gui.MainClass;
import gui.Window;
import items.Apparatus;
import items.Item;
import items.equipment.MeleeWeapon;
import items.equipment.Wand;
import java.io.Serializable;
import java.util.ArrayList;
import level.Area;
import logic.Distribution;
import pathfinding.Point;
import tiles.HiddenTile;

/**
 *
 * @author Adam Whittaker
 * 
 * Base actions that a creature can do.
 */
public class AIBaseActions implements Serializable{
    
    private final static long serialVersionUID = 1749300132;
    
    //Calculations for things.
    public interface calcAccuracy{
        double calc(Creature c);
    }
    public static calcAccuracy accuracyCalculation = c -> c.attributes.accuracy * (c.equipment.getWeapon() instanceof MeleeWeapon ? 1 : ((MeleeWeapon)c.equipment.getWeapon()).accuracy);
    public void resetAccuracyCalculation(){accuracyCalculation = c -> c.attributes.accuracy * (c.equipment.getWeapon() instanceof MeleeWeapon ? 1 : ((MeleeWeapon)c.equipment.getWeapon()).accuracy);}
    public interface calcDexterity{
        double calc(Creature c);
    }
    public calcDexterity dexterityCalculation = c -> c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);
    public void resetDexterityCalculation(){accuracyCalculation = c -> c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);}
    
    
    
    /**
     * Moves a creature in the given direction.
     * @param c The creature to be moved.
     * @param dir The displacement vector of movement.
     */
    public void move(Creature c, Integer[] dir){
        c.setXY(c.x+dir[0], c.y+dir[1]);
        c.changeAnimation("move");
        if(c.attributes.ai.destinationx==c.x&&c.attributes.ai.destinationy==c.y){
            c.changeAnimation("stand");
        }
    }
    
    /**
     * Buys a purchasable heap.
     * @param c The consumer.
     * @param heap The product.
     * @param area The market area.
     */
    public void buy(Creature c, PurchasableHeap heap, Area area){
        if(c.inventory.amountOfMoney>=heap.price){
            try{
                c.inventory.push(heap.pop());
                area.replaceHeap(heap.x, heap.y, new Floor(heap.x, heap.y));
            }catch(ReceptacleOverflowException e){
                area.replaceHeap(heap.x, heap.y, new Floor(heap.items.get(0), heap.x, heap.y));
            }
            c.inventory.setMoneyAmount(c.inventory.amountOfMoney-heap.price);
        }
    }
    
    /**
     * Sells an Item
     * @param c The seller.
     * @param item The product.
     * @param price The price.
     */
    public void sell(Creature c, Item item, int price){
        c.inventory.setMoneyAmount(c.inventory.amountOfMoney+price);
        c.inventory.items.remove(item);
    }
    
    /**
     * Puts a creature to sleep.
     * @param c The creature.
     */
    public void sleep(Creature c){
        ((IntelligentAI1)c.attributes.ai).state = EnState.SLEEPING;
        c.changeAnimation("sleep");
    }
    
    /**
     * Wakes the creature up.
     * @param c The creature.
     */
    public void wakeUp(Creature c){
        ((IntelligentAI1)c.attributes.ai).state = EnState.WANDERING;
        c.changeAnimation("stand");
    }
    
    /**
     * Wakes the creature up due to a disturbance.
     * @param c The creature.
     * @param xOfDisturbance The x coord of the disturbance.
     * @param yOfDisturbance The y coord of the disturbance.
     */
    public void wakeUp(Creature c, int xOfDisturbance, int yOfDisturbance){
        ((IntelligentAI1)c.attributes.ai).state = EnState.HUNTING;
        c.attributes.ai.setDestination(xOfDisturbance, yOfDisturbance);
        c.changeAnimation("stand");
    }
    
    /**
     * A creature attacks another creature.
     * @param attack 
     * @param attacked
     */
    public void attack(Attack attack, Creature attacked){
        if(successfulHit(attack, attacked)){
            attacked.getAttacked(attack); 
        }
    }
    
    /**
     * When a creature attacks a Hero.
     * @param attack
     * @param attacked
     */
    public void attack(Attack attack, Hero attacked){
        if(AIPlayerActions.successfulHit(attack, attacked))
            attacked.getAttacked(attack);
    }
    
    /**
     * Calculates whether a hit was successful.
     * @param attack
     * @param attacked
     * @return true if is was, false if not.
     */
    public boolean successfulHit(Attack attack, Creature attacked){
        double attackedDexterity = dexterityCalculation.calc(attacked);
        return Distribution.randomDouble(0, attack.accuracy) >=
                Distribution.randomDouble(0, attackedDexterity);
    }
    
    /**
     * Equips an item.
     * @param main The MainClass
     * @param c The equipment wearer.
     * @param eq The equipment.
     * @param choiceOfAmulet The choice of amulet to replace.
     * @return The equipment that was removed in order to make space for the new
     * equipment.
     */
    public Apparatus equip(MainClass main, Creature c, Apparatus eq, int... choiceOfAmulet){
        return c.equipment.equip(main, eq, choiceOfAmulet);
    }
      
    /**
     * Draws a wand arc on the screen and broadcasts the event.
     * @param c The firing creature.
     * @param wand The wand that is being fired.
     * @param destx The x coordinate of the destination.
     * @param desty The y coordinate of the destination.
     */
    public void fireWand(Creature c, Wand wand, int destx, int desty){
        wand.fire(c, destx, desty);
    }
    
    public void search(Creature c, Area area){
        ArrayList<Point> ary = new ArrayList<>();
        boolean searchSuccessful = false;
        for(int yPlus=-1;yPlus<2;yPlus++){
            for(int xPlus=-1;xPlus<2;xPlus++){
                if(yPlus!=0&&xPlus!=0){
                    ary.add(new Point(c.x+xPlus, c.y+yPlus));
                    if(area.map[c.y+yPlus][c.x+xPlus] instanceof HiddenTile && ((HiddenTile) area.map[c.y+yPlus][c.x+xPlus]).hidden){
                        ((HiddenTile) area.map[c.y+yPlus][c.x+xPlus]).find(c);
                        searchSuccessful = true;
                    }
                }
            }
        }
        Window.main.searchAnimation(ary, searchSuccessful);
    }
    
}
