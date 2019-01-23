
package dialogues;

import ai.PlayerAI;
import creatureLogic.Action.ActionOnItem;
import creatureLogic.Expertise;
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
     * @param item The item to display.
     * @param h The Hero.
     * @param s
     * @Unfinished Bad coding!
     */
    public ItemDialogue(Item item, Hero h, int s){
        super(((item instanceof ScreenItem && ((ScreenItem)item).onOpeningDialogue() ? "" : "") + item.name + "\n\n" + item.description.getDescription(h.expertise)), 
                (String) null, ItemAction.toStringArray(item.actions));
        this.item = item;
        hero = h;
        slot = s;
    }

    @Override
    public void screenClicked(ScreenEvent sc){
        checkDeactivate(sc);
        for(ItemAction action : item.actions) if(action.name.equals(sc.getName())){
            Window.main.setInventoryActive(false);
            ((PlayerAI)hero.attributes.ai).nextAction = new ActionOnItem(action, item, hero, -1, -1, slot);
            ((PlayerAI)hero.attributes.ai).alertAction();
        }
    }
    
}
