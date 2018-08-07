
package items.misc;

import items.Item;
import items.ItemAction;

/**
 *
 * @author Adam Whittaker
 */
public class Key extends Item{
    
    private final static long serialVersionUID = 78823214732899L;
    
    public final int depth;
    
    public Key(int d){
        super("Key", "This key opens a door", 16, 16);
        depth = d;
        actions = ItemAction.getDefaultActions(this);
    }
    
}
