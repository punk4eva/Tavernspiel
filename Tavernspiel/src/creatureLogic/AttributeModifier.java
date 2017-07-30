
package creatureLogic;

import ai.AITemplate;

/**
 *
 * @author Adam Whittaker
 */
public class AttributeModifier{
    
    public AITemplate newAi = null; //null if AITemplate is not changed.
    public double speedMultiplier = 1;
    public double attackSpeedMultiplier = 1;
    public double dexterityMultiplier = 1;
    public double maxhpMultiplier = 1;
    public double attackMultiplier = 1;
    public double regenSpeedMultiplier = 1;
    public Resistance[] newResistances = null; //null if Resistances aren't changed.
    
    public AttributeModifier(){}
    
}
