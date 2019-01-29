
package items.actions;

import creatures.Creature;
import items.Item;

/**
 *
 * @author Adam Whittaker
 * 
 * This action represents unequipping an Apparatus.
 */
public class UnequipAction extends ItemAction{

    public UnequipAction(){
        super("UNEQUIP", 3);
    }

    @Override
    public void act(Item item, Creature c, int slot, Object... data){
        c.attributes.ai.BASEACTIONS.unequip(c, item);
    }
    
}
