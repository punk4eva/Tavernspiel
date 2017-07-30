
package ai;

import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles behavior of creatures.
 */
public abstract class AITemplate implements Fileable{
    
    public EnType type;
    public int intelligence = 3;
    public MagicHexagon magic = new MagicHexagon();
    public int destinationx = -1, destinationy = -1;
    
    public enum EnType{
        PREDEFINED, HANDICAPPED, NORMAL, RANGED
    };
    
    
    @Override
    public String toFileString(){
        return type.toString() + "</ty>" + intelligence + magic.toFileString() + 
                destinationx + ":" + destinationy;
    }
    
    
    public void turn(){
        throw new UnsupportedOperationException("Unfinished AITemplate.turn()");
    }
    
}
