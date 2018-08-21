
package items.actions;

import creatures.Creature;
import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class DrinkAction extends ItemAction{

    public DrinkAction(){
        super("DRINK");
    }

    @Override
    public void act(Item i, Creature c, int x, int y, int slot, Object... data){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
