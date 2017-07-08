
package tiles;

import buffs.Buff;
import creatures.Creature;
import level.Location;
import logic.Gas;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Trap extends Tile{
    
    public boolean reusable = false;
    private Gas sprayedGas = null; //null if there is no gas.
    private Buff buff = null; //null if there is no buff.
    
    public Trap(String tile, Location loc, Buff b){
        super(tile, loc);
        buff = b;
    }
    
    public Trap(String tile, Location loc, Gas g){
        super(tile, loc);
        sprayedGas = g;
    }
    
    public void activate(Creature c){
        if(buff!=null)c.buffs.add(buff);
        else gas.merge(sprayedGas);
        if(!reusable){
            sprayedGas = null;
            buff = null;
            setIcon(ImageHandler.getImageIcon("offtrap", new Location("temp", "temporaryTiles")));
        }
    }
    
}
