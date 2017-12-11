
package items.equipment;

import items.Apparatus;
import items.Item;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Chestplate.
 */
public class Chestplate extends Apparatus{
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The description.
     * @param ic The image.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Chestplate(String s, String desc, ImageIcon ic, int dur, Distribution d, int st){
        super(s, desc, ic, dur, d, st);
        description.type = "armour";
    }
    
    /**
     * Creates a new instance.
     * @param i The Item.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Chestplate(Item i, int dur, Distribution d, int st){
        super(i.name, i.description, i.animation, dur, d, st);
        description.type = "armour";
    }
    
    public static Chestplate getArmour(String type){
        switch(type){
            case "cloth": return new ClothHelmet();
            case "leather": return new LeatherHelmet();
            case "mail": return new MailHelmet();
            case "scale": return new ScaleHelmet();
            default: return new PlateHelmet();
        }
    }
    
}
