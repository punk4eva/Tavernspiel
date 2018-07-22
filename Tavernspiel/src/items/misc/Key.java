
package items.misc;

import items.Item;
import items.ItemAction;
import items.ItemBuilder;

/**
 *
 * @author Adam Whittaker
 */
public class Key extends Item{
    
    public final int depth;
    
    public Key(int d){
        super("Key", "This key opens a door", 16, 16);
        depth = d;
        actions = ItemAction.getDefaultActions(this);
    }
    
}
