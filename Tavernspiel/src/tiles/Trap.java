
package tiles;

import blob.Blob;
import buffs.Buff;
import creatures.Creature;
import level.Location;
import listeners.StepListener;

/**
 *
 * @author Adam Whittaker
 */
public class Trap extends HiddenTile implements StepListener{
    
    public boolean reusable = false;
    public boolean used = false;
    private Blob sprayedGas = null; //null if there is no gas.
    private Buff buff = null; //null if there is no buff.
    
    public Trap(String tile, Location loc, Buff b){
        super("floor", true, false, true, tile, loc, 
                loc.feeling.trapVisibleChance.chance(), false, true, true);
        buff = b;
    }
    
    public Trap(String tile, Location loc, Blob g){
        super("floor", true, false, true, tile, loc, 
                loc.feeling.trapVisibleChance.chance(), false, true, true);
        sprayedGas = g;
    }
    
    public Trap(String tile, Location loc, Buff b, boolean hid){
        super("floor", true, false, true, tile, loc, hid, false, true, true);
        buff = b;
    }
    
    public Trap(String tile, Location loc, Blob g, boolean hid){
        super("floor", true, false, true, tile, loc, hid, false, true, true);
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
        else /*if(sprayedGas !=null)*/{
            sprayedGas.x = c.x;
            sprayedGas.y = c.y;
            c.area.addObject(sprayedGas);
        }
        if(!reusable){
            used = true;
            image = c.area.location.getImage("offtrap");
        }
    }
    
}
