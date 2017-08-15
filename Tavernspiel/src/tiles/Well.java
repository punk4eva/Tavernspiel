
package tiles;

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
        super("well");
        type = ty;
        animation.addShaders("well", loc);
    }
    
    
    
}
