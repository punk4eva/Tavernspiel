
package ai;

import creatureLogic.Action;
import creatures.Creature;
import creatures.Hero;
import gui.Window;
import items.Apparatus;
import items.ItemAction;

/**
 *
 * @author Adam Whittaker
 * 
 * This static class decodes and executes ItemActions.
 */
public class ItemActionInterpreter{
    
    /**
     * Processes an action taken by a generic Creature.
     * @param enclosing The Action that encloses this process.
     * @param action The ItemAction.
     * @param c The Creature.
     * @param slot The slot number of the Item.
     */
    public static void act(Action enclosing, ItemAction action, Creature c, int slot){
        switch(action.action){
            case "DROP": c.attributes.ai.BASEACTIONS.dropItem(c, action.item);
                break;
            case "THROW": String[] data = action.data;
                c.attributes.ai.BASEACTIONS.throwItem(c, action.item, Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                break;
            case "DRINK":
                break;
            case "READ":
                break;
            case "EQUIP":
                if(action.data==null) c.attributes.ai.BASEACTIONS.equip(c, (Apparatus)action.item, slot);
                else c.attributes.ai.BASEACTIONS.equip(c, (Apparatus)action.item, slot, Integer.parseInt(action.data[0]));
                enclosing.turns = 3.0*c.attributes.speed;
                break;
            case "UNEQUIP": 
                c.attributes.ai.BASEACTIONS.unequip(c, action.item);
                enclosing.turns = 3.0*c.attributes.speed;
                break;
        }
    }
    
    /**
     * Processes an action taken by a Hero.
     * @param enclosing The Action that encloses this process.
     * @param action The ItemAction.
     * @param c The Hero.
     * @param slot The slot number of the Item.
     */
    public static void act(Action enclosing, ItemAction action, Hero c, int slot){
        switch(action.action){
            case "DROP": c.attributes.ai.BASEACTIONS.dropItem(c, action.item);
                break;
            case "THROW": Window.main.removeViewable();
                ((AIPlayerActions)c.attributes.ai.BASEACTIONS).throwItem(c, action.item);
                break;
            case "DRINK":
                break;
            case "READ":
                break;
            case "EQUIP": 
                if(action.data==null) c.attributes.ai.BASEACTIONS.equip(c, (Apparatus)action.item, slot);
                else c.attributes.ai.BASEACTIONS.equip(c, (Apparatus)action.item, slot, Integer.parseInt(action.data[0]));
                enclosing.turns = 3.0*c.attributes.speed;
                break;
            case "UNEQUIP": 
                c.attributes.ai.BASEACTIONS.unequip(c, action.item);
                enclosing.turns = 3.0*c.attributes.speed;
                break;
        }
    }
    
}
