
package tiles;

import level.Location;
import creatures.Creature;
import creatures.Hero;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class Chasm extends Tile{
    
    public Chasm(String tileAbove, Location loc){
        super(tileAbove + "cutoff", loc);
    }
    
    public Chasm(Area area, int x, int y, Location loc){
        super(area.map[y-1][x].name + "cutoff", loc);
    }
    
    /**
     * @unfinished
     */
    public void action(Creature c){
        try{
            Hero hero = (Hero) c;
        }catch(ClassCastException e){
        
        }
    }
    
}
