
package items.wands;

import items.builders.WandBuilder;
import items.equipment.Wand;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class TwigWand extends Wand{
    
    public TwigWand(String name, int dur, double sp, Distribution wD){
        super(name, WandBuilder.getTwigInfo(wD), dur, sp);
    }
    
}
