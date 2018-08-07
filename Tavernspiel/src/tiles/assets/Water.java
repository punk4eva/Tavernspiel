
package tiles.assets;

import animation.assets.WaterAnimation;
import level.Location;
import tiles.AnimatedTile;

/**
 *
 * @author Adam Whittaker
 */
public class Water extends AnimatedTile{
    
    public final int x;
    
    public Water(Location loc, int _x){
        super(loc, _x);
        x = _x;
    }
    
    /**
     * Adds a shader to this Tile.
     * @param shaderString The string tag of the shader.
     * @param loc The Location.
     */
    public void addShaders(String shaderString, Location loc){
        ((WaterAnimation)animation).addShaders(shaderString, loc);
    }
    
}
