
package buffs;

import creatureLogic.AttributeModifier;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Buff{
    
    public String name;
    public int duration;
    public Distribution damageDistribution = null; //null if deals no damage.
    public AttributeModifier atribMod = null; //null if no attributes modified.
    
}
