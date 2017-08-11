
package tiles;

import buffs.Buff;
import creatures.Creature;
import level.Area;
import level.Location;
import logic.Gas;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Trap extends HiddenTile{
    
    public boolean reusable = false;
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
    
    public void activate(Creature c, Area area){
        if(buff!=null) c.buffs.add(buff);
        else gas.merge(sprayedGas);
        if(!reusable){
            sprayedGas = null;
            buff = null;
            setIcon(ImageHandler.getImageIcon("offtrap", area.location));
        }
    }
    
}
