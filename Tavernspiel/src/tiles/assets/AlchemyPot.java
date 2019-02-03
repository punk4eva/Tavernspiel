
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
 * This class represents an alchemy pot.
 */
public class AlchemyPot extends AnimatedTile implements Interactable{
    
    public AlchemyPot(Location loc){
        super(loc, "alchemypot", 
                new WaterAnimation("alchemypot", loc, 0), true, false, true);
        interactable = this;
    }

    @Override
    public void interact(Creature c, Area a){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double interactTurns(){
        return 1.0;
    }
    
}
