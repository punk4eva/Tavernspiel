
package enchantments;

import creatureLogic.Resistance;
import java.util.ArrayList;
import java.util.List;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * An enchantment that can be affixed to armour.
 */
public class ArmourEnchantment extends Enchantment{
    
    public List<Resistance> resistances;
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param d The distribution.
     * @param l The level.
     */
    public ArmourEnchantment(String s, Distribution d, double l){
        super(s, d, l);
        resistances = new ArrayList<>();
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param d The distribution.
     * @param l The level.
     * @param r The Resistances provided.
     */
    public ArmourEnchantment(String s, Distribution d, double l, List<Resistance> r){
        super(s, d, l);
        resistances = r;
    }
    
}
