
package items.misc;

import items.Item;
import items.actions.ItemAction;

/**
 *
 * @author Adam Whittaker
 */
public class Key extends Item{
    
    private final static long serialVersionUID = 78823214732899L;
    
    public final int depth;
    public final KeyType type;
    
    public Key(int d){
        super("Key", KeyType.NORMAL.description, 16, 16);
        depth = d;
        actions = ItemAction.getDefaultActions();
        type = KeyType.NORMAL;
    }
    
    public Key(int d, KeyType t){
        super("Key", "KEY CONSTRUCTOR", 16, 16);
        depth = d;
        actions = ItemAction.getDefaultActions();
        type = t;
        description.layers[0] = type.description;
    }
    
    /**
     * The type of Key.
     */
    public enum KeyType{
        NORMAL("This is a bulk standard key that opens a lock."),
        GOLDEN("This intricate key can open a lock with an equally intricate "
                + "design."),
        RED("This rare key is the pinnacle of craftsmanship and should probably"
                + " open some serious lock.");
        
        public final String description;
        private KeyType(String str){
            description = str;
        }
    }
    
    /**
     * Checks if the given key matches a given key description.
     * @param t
     * @param d
     * @return
     */
    public boolean matches(KeyType t, int d){
        return t.equals(type)&&d==depth;
    }
    
}
