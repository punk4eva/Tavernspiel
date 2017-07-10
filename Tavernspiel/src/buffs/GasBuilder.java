
package buffs;

import animation.Animation;
import level.Area;
import logic.Gas;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class GasBuilder{
    
    public static Gas gardengas(Area area){
        Gas g = new Gas("shadowmelded", 
                "Cleansing shafts of light pierce the vegetation.",
                BuffBuilder.shadowmelded(),
                new Animation(ImageHandler.getFrames("shadowmelded", 0)),
                1, area.zipcode);
        g.duration = 1000000;
        return g;
    }
    
}
