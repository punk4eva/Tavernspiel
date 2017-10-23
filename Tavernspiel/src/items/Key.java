
package items;

/**
 *
 * @author Adam Whittaker
 */
public class Key extends Item{
    
    public final int depth;
    
    public Key(int d){
        super("Key", "This key opens a door", ItemBuilder.getIcon(1, 1));
        depth = d;
        actions = ItemAction.getDefaultActions();
    }
    
}
