
package dialogues;

import creatureLogic.Expertise;
import gui.Window;
import items.Item;
import items.actions.ItemAction;

/**
 *
 * @author Adam Whittaker
 * 
 * A Dialogue showing an Item Description.
 */
public class ItemDialogue extends Dialogue{
    
    private final ItemAction[] actions;
    
    /**
     * Creates a new instance.
     * @param item The item to display.
     * @param expertise The expertise to judge.
     */
    public ItemDialogue(Item item, Expertise expertise){
        super(item.name + "\n\n" + item.description.getDescription(expertise), 
                (String) null, ItemAction.toStringArray(item.actions));
        actions = item.actions;
    }
    
    /**
     * Decides what to do with the Item.
     * @return The ItemAction that was selected.
     */
    public ItemAction next(){
        String ret = super.action(Window.main).getName();
        for(ItemAction act : actions){
            if(act.name.equals(ret)) return act;
        }
        return null;
    }
    
}
