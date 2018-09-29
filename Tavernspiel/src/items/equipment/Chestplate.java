
package items.equipment;

import creatureLogic.Description;
import items.builders.ItemBuilder;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Chestplate.
 */
public class Chestplate extends Armor{
    
    private final static long serialVersionUID = 12319483203459L;
    
    /**
     * Creates a new instance.
     * @param q The quality.
     * @param s The name.
     * @param desc The description.
     * @param x
     * @param y
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Chestplate(double q, String s, Description desc, int x, int y, int dur, Distribution d, int st){
        super(q, s, desc, (Serializable&Supplier<ImageIcon>)()->ItemBuilder.getIcon(x, y), dur, d, st);
        actions = standardActions(3, this);
    }
    
    public static class ClothChestplate extends Chestplate{
        
        private final static long serialVersionUID = 123191111L;
    
        public ClothChestplate(){
            super(1, "Cloth vest", new Description("armour", "This simple garment offers poor damage reduction but it's better than nothing."), 
                    0, 112, 40, new Distribution(0, 3), 10);
        }

    }
    
    public static class LeatherChestplate extends Chestplate{
        
        private final static long serialVersionUID = 1231948323232122L;
    
        public LeatherChestplate(){
            super(2, "Leather tunic", new Description("armour", "A well crafted piece of armour made from the thick skin of a monster.|Its thickness results in "
                  + "more defence but more weight and less durability."), 
                    16, 112, 52, new Distribution(2, 6), 13);
        }

    }
    
    public static class MailChestplate extends Chestplate{
        
        private final static long serialVersionUID = 1231333333L;
    
        public MailChestplate(){
            super(3, "Mail chestplate", new Description("armour", "An armour piece made of interlocking chains offering decent protection."), 
                    32, 112, 100, new Distribution(4, 9), 14);
        }

    }
    
    public static class ScaleChestplate extends Chestplate{
        
        private final static long serialVersionUID = 1231444449L;
    
        public ScaleChestplate(){
            super(4, "Scale chestplate", new Description("armour", "This exellently crafted chestplate is lightweight for those who favour mobility over defense.|It has great durability aswell."), 
                    48, 112, 180, new Distribution(4, 13), 15);
        }

    }
    
    public static class PlateChestplate extends Chestplate{
        
        private final static long serialVersionUID = 123194555459L;
    
        public PlateChestplate(){
            super(5, "Plate chestplate", new Description("armour", "More like a portable wall than a piece of armour, this towering piece offers immense protection to any strong enough to wear it."),
                    64, 112, 170, new Distribution(7, 24), 19);
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
