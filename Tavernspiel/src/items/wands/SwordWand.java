
package items.wands;

import items.builders.WandBuilder;
import items.equipment.Wand;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class SwordWand extends Wand{
    
    public SwordWand(String name, int dur, double sp, Distribution wD){
        super(name, WandBuilder.getSwordInfo(wD), dur, sp);
    }
    
}
