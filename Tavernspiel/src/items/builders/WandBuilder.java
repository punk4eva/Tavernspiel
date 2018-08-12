
package items.builders;

import items.equipment.Wand;
import items.equipment.WandPower;
import java.util.HashMap;
import level.Location;

/**
 *
 * @author Adam Whittaker
 */
public class WandBuilder{
    
    private WandBuilder(){}
    
    private static final String[] wandDescs = {
        "This wand looks unusually well crafted.",
        "The wood of this wand was worked around an especially elongated"
            + " gemstone.", "This dangerous looking battle wand was crafted to"
            + " be able to release the maximum amount of energy per blast.",
        "This wand is old: eye-shaped gem holders are a mark of ancient "
            + "craftsmanship.", "This wand is so thin that you can barely see "
            + "it.", "This wand was built by warriors and has melee "
            + "capabilities. It is designed to be stored in a holster.",
        "The skull-shaped gem of this wand seems like something that could be "
            + "worshipped as an idol.",
        "This wand is made of twigs that have been braided together."
    };
    
    public static final HashMap<String, WandPower> powerMap = new HashMap<>();
    static{
        
    }
    
    public static String getWandType(Location loc){
        throw new UnsupportedOperationException();
    }
    
    public static Wand getRandomWand(Location loc){
        throw new UnsupportedOperationException();
    }
    
}
