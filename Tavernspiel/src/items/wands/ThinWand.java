
package items.wands;

import items.builders.WandBuilder;
import items.equipment.Wand;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class ThinWand extends Wand{
    
    public ThinWand(String name, int dur, double sp, Distribution wD){
        super(name, WandBuilder.getThinInfo(wD), dur, sp);
    }
    
}
