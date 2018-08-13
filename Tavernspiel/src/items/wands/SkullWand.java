
package items.wands;

import items.builders.WandBuilder;
import items.equipment.Wand;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class SkullWand extends Wand{
    
    public SkullWand(String name, int dur, double sp, Distribution wD){
        super(name, WandBuilder.getSkullInfo(wD), dur, sp);
    }
    
}
