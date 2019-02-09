
package dialogues;

import ai.PlayerAI;
import creatureLogic.Action.ActionOnItem;
import creatures.Hero;
import gui.Window;
import gui.mainToolbox.Screen.ScreenEvent;
import items.Item;
import items.actions.ItemAction;
import listeners.ScreenItem;

/**
 *
 * @author Adam Whittaker
 * 
 * A Dialogue showing an Item Description.
 */
public class ItemDialogue extends Dialogue{
    
    private final Item item;
    private final Hero hero;
    private final int slot;
    
    
    /**
     * Creates a new instance.
     * @param i The item to display.
     * @param h The Hero.
     * @param s The slot.
     * @Unfinished Bad coding! (Perhaps do a dialogue).
     */
    public ItemDialogue(Item i, Hero h, int s){
        super(((i instanceof ScreenItem && ((ScreenItem)i).onOpeningDialogue() ? "" : "") + i.name + "\n\n" + i.description.getDescription(h.expertise)), 
                true, ItemAction.toStringArray(i.actions));
        item = i;
        hero = h;
        slot = s;
    }

    @Override
    public void screenClicked(ScreenEvent sc){
        deactivate();
        for(ItemAction action : item.actions) if(action.name.equals(sc.getName())){
            Window.main.setInventoryActive(false);
            ((PlayerAI)hero.attributes.ai).nextAction = new ActionOnItem(action, item, hero, -1, -1, slot);
            ((PlayerAI)hero.attributes.ai).alertAction();
        }
    }
    
}
