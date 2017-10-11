
package buffs;

import animation.AnimationBuilder;
import animation.GameObjectAnimator;
import creatureLogic.Description;
import blob.Gas;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds gases.
 */
public class GasBuilder{
    
    public static Gas gardengas(){
        Gas g = new Gas("shadowmelded", 
                new Description("gas", "Cleansing shafts of light pierce the vegetation."),
                BuffBuilder.shadowmelded(),
                new GameObjectAnimator(AnimationBuilder.getAnimation("shadowmelded")), 1);
        g.duration = 1000000;
        return g;
    }
    
}
