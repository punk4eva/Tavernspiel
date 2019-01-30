
package items.builders;

import items.equipment.Ring;
import items.equipment.RingProfile;
import java.io.Serializable;
import java.util.HashMap;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds Rings.
 */
public class RingBuilder implements Serializable{
    
    private static final long serialVersionUID = 78332123L;
    
    private final HashMap<String, RingProfile> ringMap = new HashMap<>();
    
    public Ring getRandomRing(Distribution ringDist, Distribution ringTypeDist){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
}
