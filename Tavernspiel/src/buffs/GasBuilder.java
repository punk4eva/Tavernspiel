
package buffs;

import animation.AnimationBuilder;
import animation.GameObjectAnimator;
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
                "Cleansing shafts of light pierce the vegetation.",
                BuffBuilder.shadowmelded(),
                new GameObjectAnimator(AnimationBuilder.getAnimation("shadowmelded")),
                1, area.zipcode, handler);
        g.duration = 1000000;
        return g;
    }
    
}
