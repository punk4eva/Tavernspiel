
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
 * This class represents Leggings.
 */
public class Leggings extends Apparatus{
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The description.
     * @param ic The image.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Leggings(String s, String desc, ImageIcon ic, int dur, Distribution d, int st){
        super(s, desc, ic, dur, d, st);
        description.type = "armour";
        actions = standardActions(3, this);
    }
    
    /**
     * Creates a new instance.
     * @param i The Item.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Leggings(Item i, int dur, Distribution d, int st){
        super(i.name, i.description, i.animation, dur, d, st);
        description.type = "armour";
        actions = standardActions(3, this);
    }
    
    public static class ClothLeggings extends Leggings{
    
        public ClothLeggings(){
            super("Cloth trousers", "These light leggings offer more warmth than protection.", 
                    ItemBuilder.getIcon(0, 128), 24, new Distribution(0, 4), 10);
        }

    }
    
    public static class LeatherLeggings extends Leggings{
    
        public LeatherLeggings(){
            super("Leather trousers", "These leggings possess the strength of the animal from which they were made.", 
                    ItemBuilder.getIcon(16, 128), 45, new Distribution(2, 6), 11);
        }

    }
    
    public static class MailLeggings extends Leggings{
    
        public MailLeggings(){
            super("Mail greaves", "The choice of most knights, these greaves offer unencumbered protection.", 
                    ItemBuilder.getIcon(32, 128), 100, new Distribution(5, 13), 13);
        }

    }
    
    public static class ScaleLeggings extends Leggings{
    
        public ScaleLeggings(){
            super("Scale greaves", "Greaves constructed by dwarvern masters have insane durability.", 
                    ItemBuilder.getIcon(48, 128), 210, new Distribution(3, 19), 17);
        }

    }
    
    public static class PlateLeggings extends Leggings{
    
        public PlateLeggings(){
            super("Plate legplates", "Lower body protection from these metal plates is unparalleled, however the metal rusts easily.", 
                    ItemBuilder.getIcon(64, 128), 150, new Distribution(6, 21), 17);
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
