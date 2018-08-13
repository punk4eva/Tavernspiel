
package items.wands;

import items.builders.WandBuilder;
import items.equipment.Wand;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class ElongatedWand extends Wand{
    
    public ElongatedWand(String name, int dur, double sp, Distribution wD){
        super(name, WandBuilder.getElongatedInfo(wD), dur, sp);
    }
    
}
