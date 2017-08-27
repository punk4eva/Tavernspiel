
package buffs;

import animation.AnimationBuilder;
import animation.GameObjectAnimator;
import creatureLogic.Description;
import gui.Handler;
import level.Area;
import logic.Gas;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds gases.
 */
public class GasBuilder{
    
    public static Gas gardengas(Area area, Handler handler){
        Gas g = new Gas("shadowmelded", 
                new Description("gas", "Cleansing shafts of light pierce the vegetation."),
                BuffBuilder.shadowmelded(),
                new GameObjectAnimator(AnimationBuilder.getAnimation("shadowmelded")),
                1, area, handler);
        g.duration = 1000000;
        return g;
    }
    
}
