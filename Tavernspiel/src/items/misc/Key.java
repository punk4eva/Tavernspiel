
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
        super("Key", KeyType.IRON.description, KeyType.IRON.x, 288);
        depth = d;
        actions = ItemAction.getDefaultActions();
        type = KeyType.IRON;
    }
    
    public Key(int d, KeyType t){
        super("Key", "KEY CONSTRUCTOR ERROR", t.x, 288);
        depth = d;
        actions = ItemAction.getDefaultActions();
        type = t;
        description.layers[0] = type.description;
    }
    
    /**
     * The type of Key.
     */
    public enum KeyType{
        IRON("This is a bulk standard key that opens a lock.", 16),
        GOLDEN("This intricate key can open a lock with an equally intricate "
                + "design.", 0),
        RED("This rare key is the pinnacle of craftsmanship and should probably"
                + " open some serious lock.", 32),
        WOODEN("This unusually common house key has rotted and warped.", 48);
        
        public final String description;
        private final int x;
        private KeyType(String str, int x_){
            description = str;
            x = x_;
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
