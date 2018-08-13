
package items.wands;

import items.builders.WandBuilder;
import items.equipment.Wand;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class AttackWand extends Wand{
    
    public AttackWand(String name, int dur, double sp, Distribution woodDistrib){
        super(name, WandBuilder.getAttackInfo(woodDistrib), dur, sp);
    }
    
}
