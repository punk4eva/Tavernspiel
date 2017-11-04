
package tiles;

import creatures.Creature;
import creatures.Hero;
import gui.Game;
import gui.Window;
import level.Location;
import listeners.StepListener;

/**
 *
 * @author Adam Whittaker
 */
public class DepthEntrance extends Tile implements StepListener{
    
    public DepthEntrance(Location loc){
        super("depthentrance", loc, true, false, true);
    }

    @Override
    public void steppedOn(Creature c){
        if(c instanceof Hero){
            ((Game) Window.main).dungeon.ascend((Hero) c);
        }
    }
    
}
