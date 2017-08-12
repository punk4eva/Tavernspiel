
package creatureLogic;

import ai.AITemplate;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 */
public class AttributeModifier implements Fileable{
    
    public AITemplate newAi = null; //null if AITemplate is not changed.
    public double speedMultiplier = 1;
    public double attackSpeedMultiplier = 1;
    public double dexterityMultiplier = 1;
    public double maxhpMultiplier = 1;
    public double attackMultiplier = 1;
    public double regenSpeedMultiplier = 1;
    public Resistance[] newResistances = null; //null if Resistances aren't changed.
    
    public AttributeModifier(){}

    @Override
    public String toFileString(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static AttributeModifier getFromFileString(String filestring){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
