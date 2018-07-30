
package tiles.assets;

import creatures.Creature;
import creatures.Hero;
import level.Area;
import level.Location;
import listeners.AreaEvent;
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
        super(area.map[y-1][x].name + "cutoff", area.location, true, false, true);
    }
    
    public Chasm(Area area, int x, int y, boolean pit){
        super(area.map[y-1][x].name + "cutoff", area.location, true, false, true);
        isPitroom = pit;
    }
    
    /**
     * @param c The creature to perform the action on.
     */
    @Unfinished
    @Override
    public void steppedOn(Creature c){
        if(!isPitroom){
            //unfinished
        }else try{
            Hero hero = (Hero) c;
            new AreaEvent("FELLINTOCHASM", c.area).notifyEvent();
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
