
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
 * This class represents Boots.
 */
public class Boots extends Apparatus{
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The description.
     * @param i The image.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Boots(String s, String desc, ImageIcon i, int dur, Distribution d, int st){
        super(s, desc, i, dur, d, st);
        description.type = "armour";
    }
    
    /**
     * Creates a new instance from an Item.
     * @param i The item to copy.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength requirement.
     */
    public Boots(Item i, int dur, Distribution d, int st){
        super(i.name, i.description, i.animation, dur, d, st);
        description.type = "armour";
    }
    
    public static class ClothBoots extends Boots{
    
        public ClothBoots(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(0, 144), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class LeatherBoots extends Boots{
    
        public LeatherBoots(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(16, 144), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class MailBoots extends Boots{
    
        public MailBoots(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(32, 144), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class ScaleBoots extends Boots{
    
        public ScaleBoots(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(48, 144), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class PlateBoots extends Boots{
    
        public PlateBoots(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(64, 144), 30, new Distribution(0, 3), 10);
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
