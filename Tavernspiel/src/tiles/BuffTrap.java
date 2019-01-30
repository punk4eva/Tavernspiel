
package tiles;

import buffs.Buff;
import creatures.Creature;
import level.Location;

/**
 *
 * @author Adam Whittaker
 * 
 * A Trap that gives the victim a Buff.
 */
public class BuffTrap extends Trap{
    
    private final Buff buff;

    public BuffTrap(String tile, String desc, Location loc, Buff b){
        super(tile, desc, loc);
        buff = b;
    }
    
    @Override
    public void steppedOn(Creature c){
        if(used) return;
        c.buffs.add(buff);
        if(!reusable){
            used = true;
            image = c.area.location.getImage("offtrap");
        }
    }

    @Override
    public BuffTrap copy(Location loc){
        return new BuffTrap(name, description.layers[0], loc, buff);
    }
    
}
