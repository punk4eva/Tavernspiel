
package items.consumables;

import items.Consumable;
import items.ItemAction;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 * 
 * This class models the Potion Class.
 */
public abstract class Potion extends Consumable{
    
    private final String tasteMessage;
    public final String unknownName;
    public final Type type;
    public enum Type{
        BENEFICIAL,VOLATILE,VERSATILE
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param un The unknown name.
     * @param desc The description.
     * @param i The image.
     * @param idd Whether this Consumable is identified.
     * @param t The type of Potion.
     * @param tm The taste message.
     */
    public Potion(String n, String un, String desc, ImageIcon i, boolean idd, Type t, String tm){
        super(n, desc, i, idd);
        unknownName = un;
        actions[2] = new ItemAction("DRINK", this);
        tasteMessage = tm;
        type = t;
        description.type = "potions";
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param un The unknown name.
     * @param desc The description.
     * @param i The image.
     * @param idd Whether this Consumable is identified.
     * @param q The quantity.
     * @param t The type of Potion.
     * @param tm The taste message.
     */
    public Potion(String n, String un, String desc, ImageIcon i, boolean idd, int q, Type t, String tm){
        super(n, desc, i, idd, q);
        type = t;
        tasteMessage = tm;
        unknownName = un;
        description.type = "potions";
        actions[2] = new ItemAction("DRINK", this);
    }
    
    /**
     * Creates a new instance.
     * @param pp The PotionProfile to base from.
     * @param idd Whether the Consumable is identified.
     */
    public Potion(PotionProfile pp, boolean idd){
        super(pp.getName(), pp.getDescription(), pp.getImageIcon(), idd);
        description.layers[0] += idd ? "\n\n" + PotionProfile.bareProfileMap.get(pp.getName()).getDescription().layers[0] : "\n\nWho knows what will happen when drunk or thrown?";
        actions[2] = new ItemAction("DRINK", this);
        unknownName = pp.unknownName;
        tasteMessage = pp.tasteMessage;
        type = pp.type;
        identified = pp.identified;
    }
    
}
