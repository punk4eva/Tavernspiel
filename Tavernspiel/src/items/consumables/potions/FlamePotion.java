
package items.consumables.potions;

import creatures.Creature;
import items.consumables.Potion;
import items.consumables.PotionProfile;

/**
 *
 * @author Adam Whittaker
 */
public class FlamePotion extends Potion{

    public FlamePotion(PotionProfile pp){
        super("Flame Potion", "This is a flame potion", pp, Type.VOLATILE);
    }

    @Override
    public boolean use(Creature c){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
