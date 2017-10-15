
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
public class DepthExit extends Tile implements StepListener{
    
    public DepthExit(Location loc){
        super("depthexit", loc, true, false);
    }
    
    @Override
    public void steppedOn(Creature c){
        if(c instanceof Hero){
            ((Game) Window.main).dungeon.descend((Hero) c);
        }
    }
    
}
