
package tiles.assets;

import animation.assets.WaterAnimation;
import level.Location;
import tiles.AnimatedTile;

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
