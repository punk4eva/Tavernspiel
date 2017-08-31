
package items.consumables;

import items.Consumable;
import items.ItemAction;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Potion extends Consumable{
    
    private final String tasteMessage;
    public final String unknownName;
    public final Type type;
    public enum Type{
        BENEFICIAL,VOLATILE,VERSATILE
    }
    
    public Potion(String n, String un, String desc, ImageIcon i, boolean idd, Type t, String tm){
        super(n, desc, i, idd);
        unknownName = un;
        actions[2] = new ItemAction("DRINK");
        tasteMessage = tm;
        type = t;
        description.type = "potions";
    }
    
    public Potion(String n, String un, String desc, ImageIcon i, boolean idd, int q, Type t, String tm){
        super(n, desc, i, idd, q);
        type = t;
        tasteMessage = tm;
        unknownName = un;
        description.type = "potions";
        actions[2] = new ItemAction("DRINK");
    }
    
    public Potion(PotionProfile pp, boolean idd){
        super(pp.getName(), pp.getDescription(), pp.getImage(), idd);
        description.layers[0] += idd ? "\n\n" + PotionProfile.bareProfileMap.get(pp.getName()).getDescription().layers[0] : "\n\nWho knows what will happen when drunk or thrown?";
        actions[2] = new ItemAction("DRINK");
        unknownName = pp.unknownName;
        tasteMessage = pp.tasteMessage;
        type = pp.type;
        identified = pp.identified;
    }
    
}
