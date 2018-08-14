
package items.consumables;

import items.Consumable;
import items.ItemAction;
import static level.Dungeon.scrollBuilder;

/**
 *
 * @author Adam Whittaker
 * 
 * This class models Scrolls.
 */
public abstract class Scroll extends Consumable{
    
    private final static long serialVersionUID = 588478994732899L;
    
    public final String rune;
    
    /**
     * Creates a new instance.
     * @param name The name of the Item.
     * @param desc The description of the Scroll's action.
     * @param sp
     */
    public Scroll(String name, String desc, ScrollProfile sp){
        super(name, sp.unknownName, sp.description + "\n\n" + desc, sp.loader,
                scrollBuilder().isIdentified(name), 1, true);
        actions[2] = new ItemAction("READ", this);
        description.type = "scrolls";
        String ary[] = unknownName.split(" ");
        rune = ary[ary.length-1];
    }
    
}
