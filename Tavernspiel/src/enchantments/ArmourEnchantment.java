
package enchantments;

import creatureLogic.Attack;
import creatureLogic.Description;
import creatureLogic.Resistance;
import creatures.Creature;
import java.util.ArrayList;
import java.util.List;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * An enchantment that can be affixed to armour.
 */
public abstract class ArmourEnchantment extends Enchantment{
    
    public List<Resistance> resistances;
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The distribution.
     * @param l The level.
     */
    public ArmourEnchantment(String s, Description desc, Distribution d, double l){
        super(s, desc, d, l);
        resistances = new ArrayList<>();
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The distribution.
     * @param l The level.
     * @param r The Resistances provided.
     */
    public ArmourEnchantment(String s, Description desc, Distribution d, double l, List<Resistance> r){
        super(s, desc, d, l);
        resistances = r;
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The distribution.
     * @param l The level.
     * @param aff The affinity of this Enchantment.
     */
    public ArmourEnchantment(String s, Description desc, Distribution d, double l, EnchantmentAffinity aff){
        super(s, desc, d, l, aff);
        resistances = new ArrayList<>();
    }
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The Description.
     * @param d The distribution.
     * @param l The level.
     * @param r The Resistances provided.
     * @param aff The affinity of this Enchantment.
     */
    public ArmourEnchantment(String s, Description desc, Distribution d, double l, List<Resistance> r, EnchantmentAffinity aff){
        super(s, desc, d, l, aff);
        resistances = r;
    }
    
    /**
     * Determines what happens when the wearer is hit.
     * @param wearer
     * @param a The Attack.
     */
    public abstract void onHit(Creature wearer, Attack a);
    
}
