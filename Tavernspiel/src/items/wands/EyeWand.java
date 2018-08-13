
package items.wands;

import items.builders.WandBuilder;
import items.equipment.Wand;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class EyeWand extends Wand{
    
    public EyeWand(String name, int dur, double sp, Distribution wD){
        super(name, WandBuilder.getEyeInfo(wD), dur, sp);
    }
    
}
