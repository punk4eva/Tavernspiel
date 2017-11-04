
package tiles;

import creatures.Creature;
import javax.swing.ImageIcon;
import level.Location;
import listeners.StepListener;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Door extends HiddenTile implements StepListener{
    
    protected final ImageIcon open;
    protected final ImageIcon closed;
    protected final ImageIcon locked;
    public boolean isLocked = false;
    protected boolean isOpen = false;
    
    public Door(Location loc){
        super("door", loc, true, true);
        open = ImageHandler.getImage("opendoor", loc);
        closed = ImageHandler.getImage("closeddoor", loc);
        locked = ImageHandler.getImage("lockeddoor", loc);
    }
    
    public Door(Location loc, boolean lock, boolean hid){
        super("door", hid ? 
                ImageHandler.getImage("wall", loc) : lock ?
                ImageHandler.getImage("lockeddoor", loc) :
                ImageHandler.getImage("closeddoor", loc), loc, hid, !lock, !lock);
        open = ImageHandler.getImage("opendoor", loc);
        closed = ImageHandler.getImage("closeddoor", loc);
        locked = ImageHandler.getImage("lockeddoor", loc);
    }
    
    public Door(Location loc, boolean lock){
        super("door", lock ?
                ImageHandler.getImage("lockeddoor", loc) :
                ImageHandler.getImage("closeddoor", loc), loc, false, true, false);
        open = ImageHandler.getImage("opendoor", loc);
        closed = ImageHandler.getImage("closeddoor", loc);
        locked = ImageHandler.getImage("lockeddoor", loc);
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
        image = open;
    }
    
    public void close(){
        isOpen = false;
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
