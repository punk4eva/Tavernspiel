
package tiles.assets;

import animation.assets.WaterAnimation;
import creatures.Creature;
import level.Area;
import level.Location;
import listeners.Interactable;
import tiles.AnimatedTile;

/**
 *
 * @author Adam Whittaker
 * 
 * @unfinished
 */
public class Well extends AnimatedTile implements Interactable{
    
    protected String type;
    
    public Well(String ty, Location loc){
        super(loc, "well", new WaterAnimation("well", loc, 0), true, false, true);
        type = ty;
    }

    @Override
    public void interact(Creature c, Area a){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double interactTurns(){
        return 1.0;
    }
    
    public static String getDescription(String ty){
        return "UNFINISHED";
    }
    
}
