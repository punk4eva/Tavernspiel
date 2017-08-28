
package dialogues;

import gui.MainClass;
import items.Item;
import items.ItemAction;

/**
 *
 * @author Adam Whittaker
 */
public class ItemDialogue extends Dialogue{
    
    private final ItemAction[] actions;
    
    public ItemDialogue(Item item){
        super(item.name + "\n\n" + item.description, (String) null, ItemAction.toStringArray(item.actions));
        actions = item.actions;
    }
    
    public ItemAction next(MainClass main){
        String ret = super.action(main).getName();
        for(ItemAction act : actions){
            if(act.getName().equals(ret)) return act;
        }
        return null;
    }
    
}
