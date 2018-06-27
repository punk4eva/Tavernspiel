
package ai;

import creatures.Creature;
import creatures.Hero;
import gui.Window;
import items.Apparatus;
import items.ItemAction;

/**
 *
 * @author Adam Whittaker
 */
public class ItemActionInterpreter{
    
    public static void act(ItemAction action, Creature c, int slot){
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
                break;
        }
    }
    
    public static void act(ItemAction action, Hero c, int slot){
        switch(action.action){
            case "DROP": c.attributes.ai.BASEACTIONS.dropItem(c, action.item);
                break;
            case "THROW": if(Window.main.viewablesSize()>1) Window.main.removeTopViewable();
                ((AIPlayerActions)c.attributes.ai.BASEACTIONS).throwItem(c, action.item);
                break;
            case "DRINK":
                break;
            case "READ":
                break;
            case "EQUIP": 
                if(action.data==null) c.attributes.ai.BASEACTIONS.equip(c, (Apparatus)action.item, slot);
                else c.attributes.ai.BASEACTIONS.equip(c, (Apparatus)action.item, slot, Integer.parseInt(action.data[0]));
                break;
        }
    }
    
}
