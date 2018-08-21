
package items.actions;

import ai.AIPlayerActions;
import creatures.Creature;
import creatures.Hero;
import gui.Window;
import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class ThrowAction extends ItemAction{
    
    protected ThrowAction(){
        super("THROW");
    }

    @Override
    public void act(Item i, Creature c, int x, int y, int slot, Object... data){
        if(c instanceof Hero){
            Window.main.removeViewable();
            ((AIPlayerActions)c.attributes.ai.BASEACTIONS).throwItem((Hero)c, i);
        }else{
            c.attributes.ai.BASEACTIONS.throwItem(c, i, x, y);
        }
    }
    
}
