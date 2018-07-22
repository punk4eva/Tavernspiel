
package tiles;

import animation.WaterAnimation;
import level.Location;

/**
 *
 * @author Adam Whittaker
 * 
 * @unfinished
 */
public class Well extends AnimatedTile{
    
    protected String type;
    
    public Well(String ty, Location loc){
        super("well", new WaterAnimation("well", loc, 0));
        type = ty;
    }
    
    
    
}
