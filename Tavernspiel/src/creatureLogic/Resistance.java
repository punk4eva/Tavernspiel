
package creatureLogic;

import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles resistances (and vulnerabilities) of creatures to certain buffs.
 */
public class Resistance{
    
    public String buffName;
    public Distribution distrib;
    
    public Resistance(String bn, Distribution dist){
        buffName = bn;
        distrib = dist;
    }
    
}
