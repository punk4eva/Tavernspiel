
package items.misc;

import creatureLogic.Description;
import items.Item;
import items.ItemAction;
import items.ItemBuilder;

/**
 *
 * @author Adam Whittaker
 */
public class Gold extends Item{
    
    public Gold(int quantity){
        super("Gold", new Description("gold", "This is a pile of " + quantity + 
                " gold coins."), ItemBuilder.getIcon(96, 16), quantity);
        actions = new ItemAction[]{};
    }
    
}
