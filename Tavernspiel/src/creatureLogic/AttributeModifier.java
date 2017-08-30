
package creatureLogic;

import ai.AITemplate;
import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 * 
 * Stores changes in attributes from buffs.
 */
public class AttributeModifier implements Serializable{
    
    public AITemplate newAi = null; //null if AITemplate is not changed.
    public double speedMultiplier = 1;
    public double attackSpeedMultiplier = 1;
    public double dexterityMultiplier = 1;
    public double maxhpMultiplier = 1;
    public double attackMultiplier = 1;
    public double regenSpeedMultiplier = 1;
    public Resistance[] newResistances = null; //null if Resistances aren't changed.
    
}
