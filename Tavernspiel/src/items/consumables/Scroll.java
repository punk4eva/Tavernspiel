
package items.consumables;

import items.Consumable;
import items.actions.ItemAction;
import items.builders.DescriptionBuilder;
import items.builders.ScrollBuilder.ScrollRecord;

/**
 *
 * @author Adam Whittaker
 * 
 * This class models Scrolls.
 */
public abstract class Scroll extends Consumable{
    
    private final static long serialVersionUID = 588478994732899L;
    
    /**
     * Creates a new instance.
     * @param name The name of the Item.
     * @param desc The description of the Scroll's action.
     * @param sr The ScrollRecord
     */
    public Scroll(String name, String desc, ScrollRecord sr){
        super(name, "Scroll of " + sr.getRune(), desc, DescriptionBuilder.getScrollDescription(sr.getRune()), sr.getLoader(), sr.isIdentified(), 1, true);
        actions[2] = ItemAction.READ;
        description.type = "scrolls";
    }
    
}
