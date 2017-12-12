
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
            super("Cloth slippers", "More useful at bedtime, these slippers offer almost no protection. But they feel comfortable.", 
                    ItemBuilder.getIcon(0, 144), 30, new Distribution(0, 2), 9);
        }

    }
    
    public static class LeatherBoots extends Boots{
    
        public LeatherBoots(){
            super("Leather shoes", "The tough leather of these leggings offer some protection.", 
                    ItemBuilder.getIcon(16, 144), 45, new Distribution(1, 4), 11);
        }

    }
    
    public static class MailBoots extends Boots{
    
        public MailBoots(){
            super("Mail stockings", "The interlocking rings of metal provide safety against blunt attacks.", 
                    ItemBuilder.getIcon(32, 144), 61, new Distribution(2, 5), 13);
        }

    }
    
    public static class ScaleBoots extends Boots{
    
        public ScaleBoots(){
            super("Scale boots", "Guaranteed to let the user walk away from any fight, most of the time.", 
                    ItemBuilder.getIcon(48, 144), 120, new Distribution(3, 7), 15);
        }

    }
    
    public static class PlateBoots extends Boots{
    
        public PlateBoots(){
            super("Plate boots", "Almost no sharp object can penetrate the plate of the legendary plate boots.", 
                    ItemBuilder.getIcon(64, 144), 90, new Distribution(4, 9), 17);
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
