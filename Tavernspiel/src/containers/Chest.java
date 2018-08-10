
package containers;

import creatures.Creature;
import items.Item;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Area;
import listeners.Interactable;
import logic.ConstantFields;
import logic.SoundHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Chest extends Receptacle implements Interactable{
    
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
     * @param desc The description.
     * @param item The item within.
     * @param x
     * @param y
     */
    public Chest(Supplier<ImageIcon> ic, String desc, Item item, int x, int y){
        super(ic, 1000, desc, x, y);
        add(item);
    }
    
    /**
     * Removes the chest and puts the contents on the ground
     * @param c The Creature that interacts.
     * @param area The area in which this chest is located.
     */
    @Override
    public void interact(Creature c, Area area){
        area.removeReceptacle(x, y);
        forEach((i) -> {
            area.plop(i, x, y);
        });
        SoundHandler.SFX("Misc/openChest.wav");
    }

    @Override
    public double interactTurns(){
        return 1;
    }
    
}
