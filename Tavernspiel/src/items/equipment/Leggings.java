
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
    }
    
    public static class ClothLeggings extends Leggings{
    
        public ClothLeggings(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(0, 128), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class LeatherLeggings extends Leggings{
    
        public LeatherLeggings(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(16, 128), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class MailLeggings extends Leggings{
    
        public MailLeggings(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(32, 128), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class ScaleLeggings extends Leggings{
    
        public ScaleLeggings(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(48, 128), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class PlateLeggings extends Leggings{
    
        public PlateLeggings(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(64, 128), 30, new Distribution(0, 3), 10);
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
