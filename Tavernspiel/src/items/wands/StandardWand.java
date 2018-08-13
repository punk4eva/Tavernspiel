
package items.wands;

import items.builders.WandBuilder;
import items.equipment.Wand;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class StandardWand extends Wand{
    
    public StandardWand(String name, int dur, double sp, Distribution wD){
        super(name, WandBuilder.getStandardInfo(wD), dur, sp);
    }
    
}
