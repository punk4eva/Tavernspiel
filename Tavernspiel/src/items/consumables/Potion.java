
package items.consumables;

import items.Consumable;
import items.ItemAction;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Potion extends Consumable{
    
    public Potion(String n, String desc, ImageIcon i, boolean idd){
        super(n, desc, i, idd);
        actions[2] = new ItemAction("DRINK");
        description.type = "potions";
    }
    
    public Potion(String n, String desc, ImageIcon i, boolean idd, int q){
        super(n, desc, i, idd, q);
        description.type = "potions";
        actions[2] = new ItemAction("DRINK");
    }
    
    public Potion(PotionProfile pp, boolean idd){
        super(pp.name, pp.description, pp.image, idd);
        description.layers[0] += idd ? "\n\n" + PotionDescriptions.pd.get(name) : "\n\nWho knows what will happen when drunk or thrown?";
        actions[2] = new ItemAction("DRINK");
    }
    
}
