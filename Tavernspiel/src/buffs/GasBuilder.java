
package buffs;

import animation.Animation;
import animation.GameObjectAnimator;
import gui.Handler;
import level.Area;
import logic.Gas;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class GasBuilder{
    
    public static Gas gardengas(Area area, Handler handler){
        Gas g = new Gas("shadowmelded", 
                "Cleansing shafts of light pierce the vegetation.",
                BuffBuilder.shadowmelded(),
                new GameObjectAnimator(new Animation(ImageHandler.getFrames("shadowmelded", 0))),
                1, area.zipcode, handler);
        g.duration = 1000000;
        return g;
    }
    
}
