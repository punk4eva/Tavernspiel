
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
    
    public String buffName;
    public Distribution distrib;
    
    public Resistance(String bn, Distribution dist){
        buffName = bn;
        distrib = dist;
    }   
    
}
