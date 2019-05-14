
package items.actions;

import creatures.Creature;
import items.Item;
import items.consumables.Potion;

/**
 *
 * @author Adam Whittaker
 * 
 * This action represents drinking a consumable.
 */
public class DrinkAction extends ItemAction{

    public DrinkAction(){
        super("DRINK");
    }

    @Override
    public void act(Item i, Creature c, int slot, Object... data){
        ((Potion) i).drinkPotion(c);
    }
    
}
