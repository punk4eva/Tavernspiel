
package items.actions;

import creatures.Creature;
import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class DropAction extends ItemAction{

    protected DropAction(){
        super("DROP");
    }

    @Override
    public void act(Item i, Creature c, int x, int y, int slot, Object... data){
        c.attributes.ai.BASEACTIONS.dropItem(c, i);
    }
    
}
