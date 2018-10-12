
package items;

import items.actions.ItemAction;
import creatureLogic.Description;
import creatures.Creature;
import java.util.function.Supplier;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Consumable extends Item implements Usable{
    
    private final static long serialVersionUID = 123567382L;
    
    public final String unknownName;
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param uN The unknown name of the Consumable.
     * @param desc The description of the Item.
     * @param lo
     * @param idd Whether the Item is identified.
     */
    public Consumable(String n, String uN, String desc, Supplier<ImageIcon> lo, boolean idd){
        super(n, desc, lo, true);
        unknownName = uN;
        identified = idd;
        actions = ItemAction.getArray(3);
    }
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param uN The unknown name of the Consumable.
     * @param desc The description of the Item.
     * @param lo
     * @param idd Whether the Item is identified.
     * @param q The quantity of the Item.
     */
    public Consumable(String n, String uN, String desc, Supplier<ImageIcon> lo, boolean idd, int q){
        super(n, desc, lo, q);
        identified = idd;
        unknownName = uN;
        actions = ItemAction.getArray(3);
    }
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param uN The unknown name of the Consumable.
     * @param desc The description of the Item.
     * @param lo
     * @param idd Whether the Item is identified.
     * @param q The quantity of the Item.
     * @param flam Whether the Item is flammable.
     */
    public Consumable(String n, String uN, String desc, Supplier<ImageIcon> lo, boolean idd, int q, boolean flam){
        super(n, desc, lo, q, flam);
        identified = idd;
        unknownName = uN;
        actions = ItemAction.getArray(3);
    }
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param uN The unknown name of the Consumable.
     * @param desc The description of the Item.
     * @param lo
     * @param idd Whether the Item is identified.
     */
    public Consumable(String n, String uN, Description desc, Supplier<ImageIcon> lo, boolean idd){
        super(n, desc, lo, 1);
        identified = idd;
        unknownName = uN;
        actions = ItemAction.getArray(3);
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
    
    @Override
    public String toString(){
        if(identified) return name;
        else return unknownName;
    }
    
}
