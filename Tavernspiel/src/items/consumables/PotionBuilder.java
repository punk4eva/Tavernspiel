
package items.consumables;

import items.consumables.potions.FlamePotion;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class PotionBuilder{
    
    @Unfinished("Placeholder")
    public static FlamePotion flamePotion(){
        return new FlamePotion(PotionProfile.getRandomProfile("flame potion", "Potion in a spherical jar", false), false);
    }
    
}
