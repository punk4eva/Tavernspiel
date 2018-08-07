
package tiles.assets;

import animation.assets.WaterAnimation;
import tiles.AnimatedTile;
import level.Location;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents an alchemy pot.
 */
public class AlchemyPot extends AnimatedTile{
    
    public AlchemyPot(Location loc){
        super("alchemypot", new WaterAnimation("alchemypot", loc, 0));
    }
    
}
