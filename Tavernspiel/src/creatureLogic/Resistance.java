
package creatureLogic;

import java.io.Serializable;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles resistances (and vulnerabilities) of creatures to certain buffs.
 */
public class Resistance implements Serializable{
    
    private final static long serialVersionUID = -437701543;
    
    public String buffName;
    public Distribution distrib;
    
    /**
     * Creates a new instance.
     * @param bn The buff to be resisted.
     * @param dist The Distribution to represent imperfect resistance.
     */
    public Resistance(String bn, Distribution dist){
        buffName = bn;
        distrib = dist;
    }   
    
}
