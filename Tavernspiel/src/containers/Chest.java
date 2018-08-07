
package containers;

import items.Item;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Area;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 */
public class Chest extends Receptacle{
    
    private final static long serialVersionUID = 8590327;
    
    /**
     * Creates a new chest instance containing an item.
     * @param item The item within.
     * @param x
     * @param y
     */
    public Chest(Item item, int x, int y){
        super((Serializable & Supplier<ImageIcon>)() -> ConstantFields.chestIcon, 1, "You won't know what's inside until you open it!", x, y);
        add(item);
    }
    
    /**
     * Creates a new chest instance containing an item.
     * @param ic The icon.
     * @param item The item within.
     * @param x
     * @param y
     */
    public Chest(Supplier<ImageIcon> ic, Item item, int x, int y){
        super(ic, 1, "You won't know what's inside until you open it!", x, y);
        add(item);
    }
    
    /**
     * Removes the chest and puts the contents on the ground
     * @param area The area in which this chest is located.
     */
    public void open(Area area){
        throw new UnsupportedOperationException("Unfinished.");
        //Remove chest and put item on ground.
    }
    
}
