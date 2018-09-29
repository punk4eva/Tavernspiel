
package items.equipment;

import creatureLogic.Description;
import enchantments.Enchantment;
import items.Apparatus;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a ring.
 */
public class Ring extends Apparatus{
    
    private final static long serialVersionUID = 58843820946389L;
    
    /**
     * Creates a new instance.
     * @param name The name.
     * @param dur The durability.
     * @param g The Enchantment.
     * @param rp The RingProfile.
     */
    public Ring(String name, int dur, Enchantment g, RingProfile rp){
        super(name, new Description("rings", rp.description), rp.loader, dur);
        enchantment = g;
        description.type = "amulets";
    }
    
    @Override
    public void upgrade(){
        level++;
        maxDurability += 10;
        durability = maxDurability;
    }
    
}
