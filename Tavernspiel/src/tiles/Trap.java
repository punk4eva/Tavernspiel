
package tiles;

import buffs.Buff;
import creatures.Creature;
import level.Location;
import listeners.StepListener;
import logic.Gas;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Trap extends HiddenTile implements StepListener{
    
    public boolean reusable = false;
    public boolean used = false;
    private Gas sprayedGas = null; //null if there is no gas.
    private Buff buff = null; //null if there is no buff.
    
    public Trap(String tile, Location loc, Buff b){
        super("floor", tile, loc, false, true);
        buff = b;
    }
    
    public Trap(String tile, Location loc, Gas g){
        super("floor", tile, loc, false, true);
        sprayedGas = g;
    }
    
    public Trap(String tile, Location loc, Buff b, boolean hid){
        super("floor", tile, loc, hid, false, true);
        buff = b;
    }
    
    public Trap(String tile, Location loc, Gas g, boolean hid){
        super("floor", tile, loc, hid, false, true);
        sprayedGas = g;
    }
    
    public Trap(Trap trap){
        super(trap);
        if(trap.buff==null) sprayedGas = trap.sprayedGas;
        else buff = trap.buff;
    }
    
    @Override
    public void steppedOn(Creature c){
        if(used) return;
        if(buff!=null) c.buffs.add(buff);
        else c.area.addObject(sprayedGas);
        if(!reusable){
            used = true;
            image = ImageHandler.getImage("offtrap", c.area.location);
        }
    }
    
}
