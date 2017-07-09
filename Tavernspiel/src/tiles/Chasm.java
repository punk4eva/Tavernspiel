
package tiles;

import level.Location;
import creatures.Creature;
import creatures.Hero;
import level.Area;
import listeners.AreaEvent;

/**
 *
 * @author Adam Whittaker
 */
public class Chasm extends Tile{
    
    private final Location location;
    private boolean isPitroom = false;
    
    public Chasm(String tileAbove, Location loc){
        super(tileAbove + "cutoff", loc);
        location = loc;
    }
    
    public Chasm(Area area, int x, int y, Location loc){
        super(area.map[y-1][x].name + "cutoff", loc);
        location = loc;
    }
    
    public Chasm(Area area, int x, int y, Location loc, boolean pit){
        super(area.map[y-1][x].name + "cutoff", loc);
        location = loc;
        isPitroom = pit;
    }
    
    /**
     * @param c The creature to perform the action on.
     * @unfinished
     */
    public void action(Creature c){
        if(!isPitroom){
            //unfinished
        }else try{
            Hero hero = (Hero) c;
            location.notify(new AreaEvent("FELLINTOCHASM", c.areaCode));
        }catch(ClassCastException e){
            //c.fallAnimation();
            //gainXP(); ???
            //if(nextLevel==null){
            //    nextLevel.preLoad();
            //    for(Apparatus ap : c.equipment) nextLevel.addForcedItem(ap);
            //    for(Item i : c.inventory) nextLevel.addForcedItem(i);
            //}else{
            //    for(Apparatus ap : c.equipment) nextLevel.randomPlop(ap);
            //    for(Item i : c.inventory) nextLevel.randomPlop(i);
            //}
        }
    }
    
}
