
package tiles;

import animation.Animation;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 * 
 * @unfinished
 */
public class Well extends AnimatedTile{
    
    protected String type;
    
    public Well(String ty, Location loc){
        super("well", new Animation(ImageHandler.getWaterFrames(loc, 0), 110));
        type = ty;
        animation.addShaders("well", loc);
    }
    
    
    
}
