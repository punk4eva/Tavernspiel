
package items;

import creatureLogic.Description;
import creatures.Creature;
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
     * @param i The Item's Image. 
     * @param idd Whether the Item is identified.
     */
    public Consumable(String n, String desc, ImageIcon i, boolean idd){
        super(n, desc, i, true);
        identified = idd;
        actions = ItemAction.getArray(3);
    }
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param i The Item's Image. 
     * @param idd Whether the Item is identified.
     * @param q The quantity of the Item.
     */
    public Consumable(String n, String desc, ImageIcon i, boolean idd, int q){
        super(n, desc, i, q);
        identified = idd;
        actions = ItemAction.getArray(3);
    }
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param i The Item's Image. 
     * @param idd Whether the Item is identified.
     * @param q The quantity of the Item.
     * @param flam Whether the Item is flammable.
     */
    public Consumable(String n, String desc, ImageIcon i, boolean idd, int q, boolean flam){
        super(n, desc, i, q, flam);
        identified = idd;
        actions = ItemAction.getArray(3);
    }
    
    /**
     * Creates an instance.
     * @param n The name of the Item.
     * @param desc The description of the Item.
     * @param im The Item's Image. 
     * @param idd Whether the Item is identified.
     */
    public Consumable(String n, Description desc, ImageIcon im, boolean idd){
        super(n, desc, im, 1);
        identified = idd;
        actions = ItemAction.getArray(3);
    }
    
    /**
     * Uses this Consumable.
     * @param c
     */
    public abstract void use(Creature c);

    @Override
    public void defaultUse(Creature c, Object... data){
        use(c);
    }

    @Override
    public void use(Creature c, ItemAction act, Object... data){
        use(c);
    }
    
}
