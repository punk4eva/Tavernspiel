
package items.consumables.potions;

import creatures.Creature;
import items.consumables.Potion;
import items.consumables.PotionProfile;

/**
 *
 * @author Adam Whittaker
 */
public class FlamePotion extends Potion{

    public FlamePotion(PotionProfile pp, boolean idd){
        super(pp, idd);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
