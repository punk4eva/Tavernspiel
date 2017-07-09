
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
    
    public Well(String t, Location loc){
        super("well", loc, new Animation(ImageHandler.getFrames("water", 0)));
        type = t;
        animation.addShaders("well", loc);
    }
    
    
    
}
