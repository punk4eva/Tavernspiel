
package tiles.assets;

import creatures.Creature;
import javax.swing.ImageIcon;
import level.Location;
import listeners.StepListener;
import tiles.HiddenTile;

/**
 *
 * @author Adam Whittaker
 */
public class Door extends HiddenTile implements StepListener{
    
    protected final ImageIcon open;
    protected final ImageIcon closed;
    protected final ImageIcon locked;
    public boolean isLocked = false;
    public boolean isOpen = false;
    
    public Door(Location loc){
        super("door", loc, true, true, false);
        open = loc.getImage("opendoor");
        closed = loc.getImage("closeddoor");
        locked = loc.getImage("lockeddoor");
    }
    
    public Door(Location loc, boolean lock, boolean hid){
        super("door", hid ? 
                loc.getImage("wall") : lock ?
                loc.getImage("lockeddoor") :
                loc.getImage("closeddoor"), loc, hid, !lock, !lock, false);
        open = loc.getImage("opendoor");
        closed = loc.getImage("closeddoor");
        locked = loc.getImage("lockeddoor");
    }
    
    public Door(Location loc, boolean lock){
        super("door", lock ?
                loc.getImage("lockeddoor") :
                loc.getImage("closeddoor"), loc, false, true, false, false);
        open = loc.getImage("opendoor");
        closed = loc.getImage("closeddoor");
        locked = loc.getImage("lockeddoor");
        isLocked = lock;
        if(lock) treadable = false;
        else flammable = true;
    }
    
    
    
    public void unlock(){
        treadable = true;
        flammable = true;
        isLocked = false;
        image = closed;
    }
    
    public void open(){
        isOpen = true;
        transparent = true;
        image = open;
    }
    
    public void close(){
        isOpen = false;
        transparent = false;
        image = closed;
    }

    @Override
    public void steppedOn(Creature c){
        open();
    }

    public void stepOff(Creature c){
        if(c.area.getReceptacle(c.x, c.y)==null) close();
    }
    
}
