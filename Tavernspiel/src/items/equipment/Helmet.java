
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
 * This class represents a Helmet.
 */
public class Helmet extends Apparatus{
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The description.
     * @param ic The image.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public Helmet(String s, String desc, ImageIcon ic, int dur, Distribution d, int st){
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
    public Helmet(Item i, int dur, Distribution d, int st){
        super(i.name, i.description, i.animation, dur, d, st);
        description.type = "armour";
        actions = standardActions(3, this);
    }
    
    public static class ClothHelmet extends Helmet{
    
        public ClothHelmet(){
            super("Cloth hat", "This crude make-shift hat offers basic protection.", 
                    ItemBuilder.getIcon(0, 96), 30, new Distribution(0, 3), 10);
        }

    }
    
    public static class LeatherHelmet extends Helmet{
    
        public LeatherHelmet(){
            super("Leather cap", "A lightweight headpiece made of some tanned hide used by scouts and travellers.", 
                    ItemBuilder.getIcon(16, 96), 45, new Distribution(1, 5), 12);
        }

    }
    
    public static class MailHelmet extends Helmet{
    
        public MailHelmet(){
            super("Mail helmet", "The knight's choice of headwear. It can protect it's wearer from most melee attack.", 
                    ItemBuilder.getIcon(32, 96), 60, new Distribution(3, 8), 14);
        }

    }
    
    public static class ScaleHelmet extends Helmet{
    
        public ScaleHelmet(){
            super("Scale helmet", "Interlinked scale technology allows for protection from the heaviest of blows.|This "
                  + "armour's heavy weight comes from its reinforced scaling for increased durability.", 
                    ItemBuilder.getIcon(48, 96), 130, new Distribution(4, 10), 17);
        }

    }
    
    public static class PlateHelmet extends Helmet{
    
        public PlateHelmet(){
            super("Plate helmet", "Despite its name you cannot eat off this helmet however its protection is unchallenged.", 
                    ItemBuilder.getIcon(64, 96), 90, new Distribution(5, 14), 18);
        }

    }
    
    public static Helmet getArmour(String type){
        switch(type){
            case "cloth": return new ClothHelmet();
            case "leather": return new LeatherHelmet();
            case "mail": return new MailHelmet();
            case "scale": return new ScaleHelmet();
            default: return new PlateHelmet();
        }
    }
    
}
