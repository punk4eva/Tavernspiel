
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
 * This class represents Boots.
 */
public class Boots extends Armor{
    
    private final static long serialVersionUID = 22723243167459L;
    
    /**
     * Creates a new instance.
     * @param q The quality of the Armor.
     * @param s The name.
     * @param desc The description.
     * @param x
     * @param y
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Boots(double q, String s, Description desc, int x, int y, int dur, Distribution d, int st){
        super(q, s, desc, 
                (Serializable&Supplier<ImageIcon>)()->ItemBuilder.getIcon(x, y), 
                dur, d, st);
        actions = standardActions(3);
    }
    
    public static class ClothBoots extends Boots{
        
        private final static long serialVersionUID = 227211113459L;
    
        public ClothBoots(){
            super(1, "Cloth slippers", new Description("armour", "More useful at bedtime, these slippers offer almost no protection. Atleast they feel comfortable."), 
                    0, 144, 30, new Distribution(0, 2), 9);
        }

    }
    
    public static class LeatherBoots extends Boots{
        
        private final static long serialVersionUID = 2222222459L;
    
        public LeatherBoots(){
            super(2, "Leather shoes", new Description("armour", "The tough leather of these shoes offers some protection."), 
                    16, 144, 45, new Distribution(1, 4), 11);
        }

    }
    
    public static class MailBoots extends Boots{
        
        private final static long serialVersionUID = 2272891313333L;
        
        public MailBoots(){
            super(3, "Mail stockings", new Description("armour", "The interlocking rings of metal provide safety against blunt attacks."), 
                    32, 144, 61, new Distribution(2, 5), 13);
        }

    }
    
    public static class ScaleBoots extends Boots{
        
        private final static long serialVersionUID = 2272844444L;
    
        public ScaleBoots(){
            super(4, "Scale boots", new Description("armour", "Guaranteed to let the user walk away from any fight, most of the time."), 
                    48, 144, 120, new Distribution(3, 7), 15);
        }

    }
    
    public static class PlateBoots extends Boots{
        
        private final static long serialVersionUID = 227255555L;
    
        public PlateBoots(){
            super(5, "Plate boots", new Description("armour", "Boots with solid steel plating. Nothing can penetrate this legendary footwear."), 
                    64, 144, 90, new Distribution(4, 9), 17);
        }

    }
    
    public static Boots getArmour(String type){
        switch(type){
            case "cloth": return new ClothBoots();
            case "leather": return new LeatherBoots();
            case "mail": return new MailBoots();
            case "scale": return new ScaleBoots();
            default: return new PlateBoots();
        }
    }
    
}
