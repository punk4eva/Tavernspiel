
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
            super("Cloth vest", "This simple garment offers poor damage reduction but it's better than nothing.", 
                    ItemBuilder.getIcon(0, 112), 40, new Distribution(0, 3), 10);
        }

    }
    
    public static class LeatherChestplate extends Chestplate{
    
        public LeatherChestplate(){
            super("Leather tunic", "A well crafted piece of armour made from the thick skin of a monster.|Its thickness results in "
                  + "more defence but more weight and less durability.", 
                    ItemBuilder.getIcon(16, 112), 52, new Distribution(2, 6), 13);
        }

    }
    
    public static class MailChestplate extends Chestplate{
    
        public MailChestplate(){
            super("Mail chestplate", "An armour piece made of interlocking chains offering decent protection.", 
                    ItemBuilder.getIcon(32, 112), 100, new Distribution(4, 9), 14);
        }

    }
    
    public static class ScaleChestplate extends Chestplate{
    
        public ScaleChestplate(){
            super("Scale chestplate", "This exellently crafted chestplate is lightweight for those who favour mobility over defense.|It has great durability aswell.", 
                    ItemBuilder.getIcon(48, 112), 180, new Distribution(4, 13), 15);
        }

    }
    
    public static class PlateChestplate extends Chestplate{
    
        public PlateChestplate(){
            super("Plate chestplate", "More like a portable wall than a piece of armour, this towering piece offers immense protection to any strong enough to wear it.",
                    ItemBuilder.getIcon(64, 112), 170, new Distribution(7, 24), 19);
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
