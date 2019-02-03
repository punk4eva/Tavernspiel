
package tiles.assets;

import creatures.Creature;
import creatures.Hero;
import level.Area;
import level.Location;
import listeners.StepListener;
import logic.Utils.Unfinished;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class Chasm extends Tile implements StepListener{
    
    private boolean isPitroom = false;
    
    public Chasm(String tileAbove, Location loc){
        super(tileAbove.equals("void") ? "void" : tileAbove + "cutoff", loc, true, false, true);
    }
    
    public Chasm(Area area, int x, int y){
        super(area.map[y-1][x] instanceof Chasm ? "void" : area.map[y-1][x].name + "cutoff", area.location, true, false, true);
        name = "void";
    }
    
    public Chasm(Area area, int x, int y, boolean pit){
        super(area.map[y-1][x] instanceof Chasm ? "void" : area.map[y-1][x].name + "cutoff", area.location, true, false, true);
        name = "void";
        isPitroom = pit;
    }
    
    /**
     * @param c The creature to perform the action on.
     */
    @Unfinished
    @Override
    public void steppedOn(Creature c){
        if(!isPitroom){
            //@Unfinished
        }else if(c instanceof Hero){
            Hero hero = (Hero) c;
            //@Unfinished
        }else{
            //@Unfinished
        }
    }
    
}
