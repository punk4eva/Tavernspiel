
package ai;

import creatures.Creature;
import creatures.Hero;
import gui.Window;
import items.ItemAction;

/**
 *
 * @author Adam Whittaker
 */
public class ItemActionInterpreter{
    
    public static void act(ItemAction action, Creature c){
        switch(action.getName()){
            case "DROP": c.attributes.ai.BASEACTIONS.dropItem(c, action.getItem());
                break;
            case "THROW": String[] data = action.getData();
                c.attributes.ai.BASEACTIONS.throwItem(c, action.getItem(), Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                break;
            case "DRINK":
                break;
            case "READ":
                break;
        }
    }
    
    public static void act(ItemAction action, Hero c){
        switch(action.getName()){
            case "DROP": c.attributes.ai.BASEACTIONS.dropItem(c, action.getItem());
                break;
            case "THROW": if(Window.main.viewablesSize()>1) Window.main.removeTopViewable();
                ((AIPlayerActions)c.attributes.ai.BASEACTIONS).throwItem(c, action.getItem());
                break;
            case "DRINK":
                break;
            case "READ":
                break;
        }
    }
    
}
