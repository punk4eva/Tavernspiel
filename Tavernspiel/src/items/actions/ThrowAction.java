
package items.actions;

import creatures.Creature;
import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class ThrowAction extends LocationItemAction{
    
    protected ThrowAction(){
        super("THROW", "Select a tile to throw to.", null);
    }

    @Override
    public void act(Item i, Creature c, int x, int y, int slot, Object... data){
        c.attributes.ai.BASEACTIONS.throwItem(c, i, x, y);
    }
    
}
