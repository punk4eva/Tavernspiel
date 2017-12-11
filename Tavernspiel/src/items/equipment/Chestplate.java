
package items.equipment;

import items.Apparatus;
import items.Item;
import items.ItemBuilder;
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
    
    public static class ClothChestplate extends Chestplate{
    
        public ClothChestplate(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(0, 112), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class LeatherChestplate extends Chestplate{
    
        public LeatherChestplate(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(16, 112), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class MailChestplate extends Chestplate{
    
        public MailChestplate(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(32, 112), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class ScaleChestplate extends Chestplate{
    
        public ScaleChestplate(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(48, 112), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class PlateChestplate extends Chestplate{
    
        public PlateChestplate(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(64, 112), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static Chestplate getArmour(String type){
        switch(type){
            case "cloth": return new ClothChestplate();
            case "leather": return new LeatherChestplate();
            case "mail": return new MailChestplate();
            case "scale": return new ScaleChestplate();
            default: return new PlateChestplate();
        }
    }
    
}
