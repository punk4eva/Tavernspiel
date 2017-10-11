
package blob;

import animation.GameObjectAnimator;
import buffs.BuffBuilder;
import creatureLogic.Description;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
@Unfinished("Need to override turn() and create burn method.")
public class Fire extends Gas{
    
    public Fire(GameObjectAnimator a){
        super("fire", new Description("naturals", "A fire is raging here"), BuffBuilder.fire(), a, 1);
    }
    
}
