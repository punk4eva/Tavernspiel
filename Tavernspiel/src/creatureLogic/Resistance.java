
package creatureLogic;

import logic.Distribution;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles resistances (and vulnerabilities) of creatures to certain buffs.
 */
public class Resistance implements Fileable{
    
    public String buffName;
    public Distribution distrib;
    
    public Resistance(String bn, Distribution dist){
        buffName = bn;
        distrib = dist;
    }
    
    public Resistance(){/**Only for use with Fileable*/}

    @Override
    public String toFileString(){
        return buffName + distrib.toFileString();
    }

    public static Resistance getFromFileString(String filestring){
        return new Resistance(filestring.substring(0, filestring.indexOf("[")),
            Distribution.getFromFileString(filestring.substring(
            filestring.indexOf("["))));
    }
    
    
    
}
