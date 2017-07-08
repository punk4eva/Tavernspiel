
package creatureLogic;

import ai.AI;

/**
 *
 * @author Adam Whittaker
 */
public class AttributeModifier{
    
    public AI newAi = null; //null if AI is not changed.
    public double speedMultiplier = 1;
    public double attackSpeedMultiplier = 1;
    public double dexterityMultiplier = 1;
    public double maxhpMultiplier = 1;
    public double attackMultiplier = 1;
    public Resistance[] newResistances = null; //null if Resistances aren't changed.
    
    public AttributeModifier(){}
    
}
