
package ai;

import ai.IntelligentAI1.EnState;
import containers.Floor;
import containers.PurchasableHeap;
import creatures.Creature;
import creatures.Hero;
import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import gui.MainClass;
import items.Apparatus;
import items.Item;
import items.equipment.HeldWeapon;
import items.equipment.MeleeWeapon;
import items.equipment.Wand;
import level.Area;
import level.Location;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class AIBaseActions{
    
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
    public MainClass main;
    
    
    
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
    
    public void sell(Creature c, Item item, int price){
        c.inventory.setMoneyAmount(c.inventory.amountOfMoney+price);
        c.inventory.items.remove(item);
    }
    
    public void sleep(Creature c){
        ((IntelligentAI1)c.attributes.ai).state = EnState.SLEEPING;
        c.sleepAnimation();
    }
    
    public void wakeUp(Creature c){
        ((IntelligentAI1)c.attributes.ai).state = EnState.WANDERING;
        c.standAnimation();
    }
    
    public void wakeUp(Creature c, int xOfDisturbance, int yOfDisturbance){
        ((IntelligentAI1)c.attributes.ai).state = EnState.HUNTING;
        c.attributes.ai.setDestination(xOfDisturbance, yOfDisturbance);
        c.standAnimation();
    }
    
    public void attack(Creature attacker, Creature attacked){
        if(successfulHit(attacker, attacked)){
            attacked.getAttacked(attacker, attacker.nextHit()); 
        }
    }
    
    public void attack(Creature attacker, Hero attacked){
        if(AIPlayerActions.successfulHit(attacker, attacked)){
            attacked.getAttacked(attacker, attacker.nextHit()); 
        }
    }
    
    public boolean successfulHit(Creature attacker, Creature attacked){
        double attackerAccuracy = accuracyCalculation.calc(attacker);
        double attackedDexterity = dexterityCalculation.calc(attacked);
        return Distribution.randomDouble(0, attackerAccuracy) >=
                Distribution.randomDouble(0, attackedDexterity);
    }
    
    public void equip(Creature c, Apparatus app, int... choiceOfAmulet){
        c.equipment.equip(app, choiceOfAmulet);
    }
        
    public void fireWand(Creature c, Wand wand, int destx, int desty, Location loc){
        main.drawWandArc(wand, c.x, c.y, destx, desty);
        if(wand.areaEvent!=null) wand.setAndNotify(destx, desty, loc);
    }
    
}
