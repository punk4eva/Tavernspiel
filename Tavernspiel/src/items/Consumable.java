
package items;

import creatureLogic.Description;
import creatures.Creature;
import java.util.function.Supplier;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Consumable extends Item implements Usable{
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param lo
     * @param idd Whether the Item is identified.
     */
    public Consumable(String n, String desc, Supplier<ImageIcon> lo, boolean idd){
        super(n, desc, lo, true);
        identified = idd;
        actions = ItemAction.getArray(3, this);
    }
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param lo
     * @param idd Whether the Item is identified.
     * @param q The quantity of the Item.
     */
    public Consumable(String n, String desc, Supplier<ImageIcon> lo, boolean idd, int q){
        super(n, desc, lo, q);
        identified = idd;
        actions = ItemAction.getArray(3, this);
    }
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param lo
     * @param idd Whether the Item is identified.
     * @param q The quantity of the Item.
     * @param flam Whether the Item is flammable.
     */
    public Consumable(String n, String desc, Supplier<ImageIcon> lo, boolean idd, int q, boolean flam){
        super(n, desc, lo, q, flam);
        identified = idd;
        actions = ItemAction.getArray(3, this);
    }
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param lo
     * @param idd Whether the Item is identified.
     */
    public Consumable(String n, Description desc, Supplier<ImageIcon> lo, boolean idd){
        super(n, desc, lo, 1);
        identified = idd;
        actions = ItemAction.getArray(3, this);
    }
    
    /**
     * Uses this Consumable.
     * @param c
     * @return Whether the item has been consumed during use.
     */
    public abstract boolean use(Creature c);

    @Override
    public boolean defaultUse(Creature c, Object... data){
        return use(c);
    }

    @Override
    public boolean use(Creature c, ItemAction act, Object... data){
        return use(c);
    }
    
}
