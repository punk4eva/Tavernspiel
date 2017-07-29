
package ai;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles behavior of creatures.
 */
public abstract class AI{
    
    public EnType type;
    public int intelligence = 3;
    public MagicHexagon magic = new MagicHexagon();
    
    public enum EnType{
        PLAYER, PREDEFINED, HANDICAPPED, NORMAL, RANGED
    };
    
    
}
