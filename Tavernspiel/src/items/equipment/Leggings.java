
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
 * This class represents Leggings.
 */
public class Leggings extends Armor{
    
    private final static long serialVersionUID = 588432425434732899L;
    
    /**
     * Creates a new instance.
     * @param q
     * @param s The name.
     * @param desc The description.
     * @param x
     * @param y
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Leggings(double q, String s, Description desc, int x, int y, int dur, Distribution d, int st){
        super(q, s, desc, (Serializable&Supplier<ImageIcon>)()->ItemBuilder.getIcon(x, y), dur, d, st);
        description.type = "armour";
        actions = standardActions(3);
    }
    
    public static class ClothLeggings extends Leggings{
        
        private final static long serialVersionUID = 58843242511111119L;
    
        public ClothLeggings(){
            super(1, "Cloth trousers", new Description("armour", "These light leggings offer more warmth than protection."), 
                    0, 128, 24, new Distribution(0, 4), 10);
        }

    }
    
    public static class LeatherLeggings extends Leggings{
        
        private final static long serialVersionUID = 588432425222232899L;
    
        public LeatherLeggings(){
            super(2,"Leather trousers", new Description("armour", "These leggings possess the strength of the animal from which they were made."), 
                    16, 128, 45, new Distribution(2, 6), 11);
        }

    }
    
    public static class MailLeggings extends Leggings{
        
        private final static long serialVersionUID = 58843242333339L;
    
        public MailLeggings(){
            super(3,"Mail greaves", new Description("armour", "The choice of most knights, these greaves offer unencumbered protection."), 
                    32, 128, 100, new Distribution(5, 13), 13);
        }

    }
    
    public static class ScaleLeggings extends Leggings{
        
        private final static long serialVersionUID = 588444444732899L;
    
        public ScaleLeggings(){
            super(4,"Scale greaves", new Description("armour", "Greaves constructed by dwarvern masters have insane durability."), 
                    48, 128, 210, new Distribution(3, 19), 17);
        }

    }
    
    public static class PlateLeggings extends Leggings{
        
        private final static long serialVersionUID = 58845555555555L;
    
        public PlateLeggings(){
            super(5,"Plate legplates", new Description("armour", "Lower body protection from these metal plates is unparalleled, however the metal rusts easily."), 
                    64, 128, 150, new Distribution(6, 21), 17);
        }

    }
    
    public static Leggings getArmour(String type){
        switch(type){
            case "cloth": return new ClothLeggings();
            case "leather": return new LeatherLeggings();
            case "mail": return new MailLeggings();
            case "scale": return new ScaleLeggings();
            default: return new PlateLeggings();
        }
    }
    
}
