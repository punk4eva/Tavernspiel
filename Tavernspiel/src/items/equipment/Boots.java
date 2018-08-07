
package items.equipment;

import items.Apparatus;
import items.ItemBuilder;
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
public class Boots extends Apparatus{
    
    private final static long serialVersionUID = 22723243167459L;
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The description.
     * @param x
     * @param y
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Boots(String s, String desc, int x, int y, int dur, Distribution d, int st){
        super(s, desc, 
                (Serializable&Supplier<ImageIcon>)()->ItemBuilder.getIcon(x, y), 
                dur, d, st);
        description.type = "armour";
        actions = standardActions(3, this);
    }
    
    public static class ClothBoots extends Boots{
        
        private final static long serialVersionUID = 227211113459L;
    
        public ClothBoots(){
            super("Cloth slippers", "More useful at bedtime, these slippers offer almost no protection. Atleast they feel comfortable.", 
                    0, 144, 30, new Distribution(0, 2), 9);
        }

    }
    
    public static class LeatherBoots extends Boots{
        
        private final static long serialVersionUID = 2222222459L;
    
        public LeatherBoots(){
            super("Leather shoes", "The tough leather of these shoes offers some protection.", 
                    16, 144, 45, new Distribution(1, 4), 11);
        }

    }
    
    public static class MailBoots extends Boots{
        
        private final static long serialVersionUID = 2272891313333L;
        
        public MailBoots(){
            super("Mail stockings", "The interlocking rings of metal provide safety against blunt attacks.", 
                    32, 144, 61, new Distribution(2, 5), 13);
        }

    }
    
    public static class ScaleBoots extends Boots{
        
        private final static long serialVersionUID = 2272844444L;
    
        public ScaleBoots(){
            super("Scale boots", "Guaranteed to let the user walk away from any fight, most of the time.", 
                    48, 144, 120, new Distribution(3, 7), 15);
        }

    }
    
    public static class PlateBoots extends Boots{
        
        private final static long serialVersionUID = 227255555L;
    
        public PlateBoots(){
            super("Plate boots", "Boots with solid steel plating. Nothing can penetrate this legendary footwear.", 
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
