
package tiles.assets;

import creatures.Creature;
import creatures.Hero;
import gui.Game;
import gui.Window;
import level.Area;
import level.Location;
import listeners.StepListener;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class DepthEntrance extends Tile implements StepListener{
    
    public Area previousArea, currentArea;
    
    public DepthEntrance(Location loc){
        super("depthentrance", "This is the way to the upper level.", loc, true, false, true);
    }

    @Override
    public void steppedOn(Creature c){
        if(c instanceof Hero){
            if(previousArea==null) ((Game) Window.main).dungeon.ascend((Hero) c);
            else ((Game) Window.main).dungeon.setArea((Hero) c, previousArea);
        }
    }
    
}
