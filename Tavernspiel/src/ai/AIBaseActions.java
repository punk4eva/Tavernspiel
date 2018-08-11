
package ai;

import ai.intelligence.IntelligentAI1;
import ai.intelligence.IntelligentAI1.EnState;
import animation.CreatureAnimator;
import animation.assets.WaterStepAnimation;
import containers.Floor;
import containers.PurchasableHeap;
import creatureLogic.Action;
import creatureLogic.Attack.CreatureAttack;
import creatures.Creature;
import creatures.Hero;
import gui.Window;
import gui.mainToolbox.Main;
import items.Apparatus;
import items.Item;
import items.ItemAction;
import items.equipment.MeleeWeapon;
import items.equipment.Wand;
import java.io.Serializable;
import java.util.LinkedList;
import level.Area;
import listeners.StepListener;
import logic.ConstantFields;
import logic.Distribution;
import pathfinding.Point;
import tiles.assets.Door;
import tiles.HiddenTile;
import tiles.assets.Water;

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
    public static calcAccuracy accuracyCalculation = (Serializable & calcAccuracy) c -> c.attributes.accuracy * (c.equipment.weapon instanceof MeleeWeapon ? 1 : ((MeleeWeapon)c.equipment.weapon).accuracy);
    public void resetAccuracyCalculation(){accuracyCalculation = c -> c.attributes.accuracy * (c.equipment.weapon instanceof MeleeWeapon ? 1 : ((MeleeWeapon)c.equipment.weapon).accuracy);}
    public interface calcDexterity{
        double calc(Creature c);
    }
    public calcDexterity dexterityCalculation = (Serializable & calcDexterity) c -> c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);
    public void resetDexterityCalculation(){accuracyCalculation = c -> c.attributes.dexterity / (c.equipment.strengthDifference(c.attributes.strength)<0 ? Math.pow(1.5, c.equipment.strengthDifference(c.attributes.strength)) : 1);}
    

    
    /**
     * Moves a creature in the given direction.
     * @param c The creature to be moved.
     * @param dir The displacement vector of movement.
     */
    public void move(Creature c, Integer[] dir){
        if(c.area.map[c.y][c.x] instanceof Door) ((Door)c.area.map[c.y][c.x]).stepOff(c);
        if(!((CreatureAnimator)c.animator).currentName.equals("move")) c.changeAnimation("move");
        c.smootheXY(c.x+dir[0], c.y+dir[1]);
        if(c.attributes.ai.destinationx==c.x&&c.attributes.ai.destinationy==c.y){
            c.changeAnimation("stand");
        }
        if(c.area.map[c.y][c.x] instanceof StepListener){
            ((StepListener)c.area.map[c.y][c.x]).steppedOn(c);
        }
    }
    
    /**
     * Moves a creature to the given coordinates and doesn't handle animation.
     * @param c The creature to be moved.
     * @param x
     * @param y
     */
    public void moveRaw(Creature c, int x, int y){
        if(c.area.map[c.y][c.x] instanceof Door) ((Door)c.area.map[c.y][c.x]).stepOff(c);
        c.area.graph.moveOff(c.x, c.y);
        if(c.area.map[c.y][c.x] instanceof Water) 
            Main.animator.addAnimation(new WaterStepAnimation(c.x, c.y));
        c.setXY(x, y);
        c.area.graph.moveOn(c.x, c.y);
        if(c.area.map[c.y][c.x] instanceof StepListener){
            ((StepListener)c.area.map[c.y][c.x]).steppedOn(c);
        }
        c.FOV.update(c.x, c.y, c.area);
    }
    
    /**
     * Moves a Creature to the given coordinates and orders a smooth moving
     * animation.
     * @param c
     * @param x
     * @param y
     */
    public void smootheRaw(Creature c, int x, int y){
        if(!((CreatureAnimator)c.animator).currentName.equals("move")) c.changeAnimation("move");
        ((CreatureAnimator)c.animator).updateOrientation(c.x, x);
        c.smootheXY(x, y);
    }
    
    /**
     * Waits for one turn.
     * @param c The waiter.
     */
    public void wait(Creature c){
        c.attributes.ai.paralyze(1.0);
    }
    
    /**
     * Buys a purchasable heap.
     * @param c The consumer.
     * @param heap The product.
     * @param area The market area.
     */
    public void buy(Creature c, PurchasableHeap heap, Area area){
        if(c.inventory.amountOfMoney>=heap.price){
            c.inventory.push(heap.pop());
            area.replaceHeap(heap.x, heap.y, new Floor(heap.x, heap.y));
                area.replaceHeap(heap.x, heap.y, new Floor(heap.get(0), heap.x, heap.y));
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
        c.inventory.remove(item);
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
    public void attack(CreatureAttack attack, Creature attacked){
        if(successfulHit(attack, attacked)){
            attacked.takeDamage(attack); 
        }
    }
    
    /**
     * When a creature attacks a Hero.
     * @param attack
     * @param attacked
     */
    public void attack(CreatureAttack attack, Hero attacked){
        if(AIPlayerActions.successfulHit(attack, attacked))
            attacked.takeDamage(attack);
    }
    
    /**
     * Calculates whether a hit was successful.
     * @param attack
     * @param attacked
     * @return true if is was, false if not.
     */
    public boolean successfulHit(CreatureAttack attack, Creature attacked){
        double attackedDexterity = dexterityCalculation.calc(attacked);
        return Distribution.randomDouble(0, attack.accuracy) >=
                Distribution.randomDouble(0, attackedDexterity);
    }
    
    /**
     * Equips an item.
     * @param c The equipment wearer.
     * @param eq The equipment.
     * @param slot The inventory slot which used to hold the apparatus.
     * @param choiceOfAmulet The choice of amulet to replace.
     */
    public void equip(Creature c, Apparatus eq, int slot, int... choiceOfAmulet){
        Apparatus reject = c.equipment.equip(eq, choiceOfAmulet);
        c.inventory.remove(slot);
        if(reject!=null) c.inventory.add(slot, reject);
    }
    
    /**
     * Unequips an Item.
     * @param c
     * @param item
     */
    public void unequip(Creature c, Item item){
        Apparatus reject = c.equipment.unequip((Apparatus)item);
        if(!c.inventory.add(reject)){
            c.area.plop(reject, c.x, c.y);
            Main.addMessage(ConstantFields.badColor, "Your pack is too full for the " +
                    reject.toString(3));
        }
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
    
    /**
     * Picks up an Item from the floor.
     * @param c The Creature.
     * @throws NullPointerException if there is no Receptacle.
     */
    public void pickUp(Creature c){
        Item i = c.area.pickUp(c.x, c.y);
        if(!c.inventory.add(i)){
            c.area.plop(i, c.x, c.y);
            Main.addMessage(ConstantFields.badColor, "Your pack is too full for the " +
                    i.toString(3));
        }
    }
    
    /**
     * Forwards an ItemAction.
     * @param enc The enclosing Action
     * @param c The Creature
     * @param a The ItemAction
     * @param slot The slot number of the Item
     */
    public void interpretItemAction(Action enc, Creature c, ItemAction a, int slot){
        ItemActionInterpreter.act(enc, a, c, slot);
    }
    
    /**
     * Throws an Item.
     * @param c
     * @param i
     * @param x
     * @param y
     */
    public void throwItem(Creature c, Item i, int x, int y){
        c.inventory.remove(i);
        Main.animator.throwItem(c.x, c.y, i, x, y);
        c.area.plop(i, x, y);
    }
    
    /**
     * Drops an Item.
     * @param c
     * @param i
     */
    public void dropItem(Creature c, Item i){
        c.inventory.remove(i);
        c.area.plop(i, c.x, c.y);
    }
    
    /**
     * Searches a given Area.
     * @param c
     * @param area
     */
    public void search(Creature c, Area area){
        LinkedList<Point> ary = new LinkedList<>();
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
        Main.animator.searchAnimation(ary, searchSuccessful);
    }
    
    /**
     * Checks whether the Creature can move in the given direction.
     * @param c
     * @param dir THe int vector representing the movement.
     * @return Whether the final tile of movement is free.
     */
    public boolean canMove(Creature c, Integer[] dir){
        if(dir[0]==0&&dir[1]==0) return true;
        return c.area.tileFree(c.x+dir[0], c.y+dir[1]);
    }
    
    /**
     * Interacts with this Tile.
     * @param c
     * @param area
     * @param x
     * @param y
     */
    public void interact(Creature c, Area area, int x, int y){
        if(area.map[y][x].interactable!=null){
            double turns = area.map[y][x].interactable.interactTurns();
            area.map[y][x].interactable.interact(c, area);
            c.attributes.ai.skipping += turns*c.attributes.speed;
        }
    }
    
}
