
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
public class DepthExit extends Tile implements StepListener{
    
    public Area nextArea;
    
    public DepthExit(Location loc, Area... nA){
        super("depthexit", loc, true, false, true);
        if(nA.length!=0) nextArea = nA[0];
    }
    
    @Override
    public void steppedOn(Creature c){
        if(c instanceof Hero){
            if(nextArea==null) ((Game) Window.main).dungeon.descend((Hero) c);
            else ((Game) Window.main).dungeon.setArea((Hero) c, nextArea);
        }
    }
    
}
