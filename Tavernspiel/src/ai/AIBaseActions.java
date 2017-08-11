
package ai;

import ai.IntelligentAI1.EnState;
import containers.Floor;
import containers.PurchasableHeap;
import creatures.Creature;
import creatures.Hero;
import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import gui.Window;
import items.Apparatus;
import items.Item;
import items.equipment.MeleeWeapon;
import items.equipment.Wand;
import java.util.ArrayList;
import level.Area;
import level.Location;
import logic.Distribution;
import pathfinding.Point;
import tiles.HiddenTile;

/**
 *
 * @author Adam Whittaker
 * 
 * Base actions that a creature can do.
 */
public class AIBaseActions{
    
    
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
     * Moves a creature in the given direction;
     * @param c The creature to be moved.
     * @param directionCode The [N,NE,E,SE,S,SW,W,NW] -> [1-8] direction.
     */
    public void move(Creature c, int directionCode){
        switch(directionCode){
            case 1: c.setXY(c.x-1, c.y-1);
            case 2: c.setY(c.y-1);
            case 3: c.setXY(c.x+1, c.y-1);
            case 4: c.setX(c.x-1);
            case 5: c.setX(c.x+1);
            case 6: c.setXY(c.x-1, c.y+1);
            case 7: c.setY(c.y-1);
            default: c.setXY(c.x+1, c.x+1);
        }
        c.moveAnimation();
        if(c.attributes.ai.destinationx==c.x&&c.attributes.ai.destinationy==c.y){
            c.standAnimation();
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
            }catch(ReceptacleOverflowException | ReceptacleIndexOutOfBoundsException e){
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
        c.sleepAnimation();
    }
    
    /**
     * Wakes the creature up.
     * @param c The creature.
     */
    public void wakeUp(Creature c){
        ((IntelligentAI1)c.attributes.ai).state = EnState.WANDERING;
        c.standAnimation();
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
        c.standAnimation();
    }
    
    /**
     * A creature attacks another creature.
     * @param attacker
     * @param attacked
     */
    public void attack(Creature attacker, Creature attacked){
        if(successfulHit(attacker, attacked)){
            attacked.getAttacked(attacker, attacker.nextHit()); 
        }
    }
    
    /**
     * When a creature attacks a Hero.
     * @param attacker 
     * @param attacked
     */
    public void attack(Creature attacker, Hero attacked){
        if(AIPlayerActions.successfulHit(attacker, attacked)){
            attacked.getAttacked(attacker, attacker.nextHit()); 
        }
    }
    
    /**
     * Calculates whether a hit was successful.
     * @param attacker
     * @param attacked
     * @return true if is was, false if not.
     */
    public boolean successfulHit(Creature attacker, Creature attacked){
        double attackerAccuracy = accuracyCalculation.calc(attacker);
        double attackedDexterity = dexterityCalculation.calc(attacked);
        return Distribution.randomDouble(0, attackerAccuracy) >=
                Distribution.randomDouble(0, attackedDexterity);
    }
    
    /**
     * Equips an item.
     * @param c The equipment wearer.
     * @param eq The equipment.
     * @param choiceOfAmulet The choice of amulet to replace.
     * @return The equipment that was removed in order to make space for the new
     * equipment.
     */
    public Apparatus equip(Creature c, Apparatus eq, int... choiceOfAmulet){
        return c.equipment.equip(eq, choiceOfAmulet);
    }
      
    /**
     * Draws a wand arc on the screen and broadcasts the event.
     * @param c The firing creature.
     * @param wand The wand that is being fired.
     * @param destx The x coordinate of the destination.
     * @param desty The y coordinate of the destination.
     * @param loc The location where the wand was fired.
     */
    public void fireWand(Creature c, Wand wand, int destx, int desty, Location loc){
        Window.main.drawWandArc(wand, c.x, c.y, destx, desty);
        if(wand.areaEvent!=null) wand.setAndNotify(destx, desty, loc);
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
        Window.main.search(ary, area, searchSuccessful);
    }
    
}
