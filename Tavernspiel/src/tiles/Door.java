
package tiles;

import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Door extends HiddenTile{
    
    protected final ImageIcon open;
    protected final ImageIcon closed;
    protected final ImageIcon locked;
    protected boolean isLocked = false;
    protected boolean isOpen = false;
    
    public Door(Location loc){
        super("door", loc, true, true);
        open = ImageHandler.getImageIcon("opendoor", loc);
        closed = ImageHandler.getImageIcon("closeddoor", loc);
        locked = ImageHandler.getImageIcon("lockeddoor", loc);
    }
    
    public Door(Location loc, boolean lock, boolean hid){
        super("door", hid ? 
                ImageHandler.getImageIcon("wall", loc) : lock ?
                ImageHandler.getImageIcon("lockeddoor", loc) :
                ImageHandler.getImageIcon("closeddoor", loc), loc, hid, !lock, !lock);
        open = ImageHandler.getImageIcon("opendoor", loc);
        closed = ImageHandler.getImageIcon("closeddoor", loc);
        locked = ImageHandler.getImageIcon("lockeddoor", loc);
    }
    
    public Door(Location loc, boolean lock){
        super("door", lock ?
                ImageHandler.getImageIcon("lockeddoor", loc) :
                ImageHandler.getImageIcon("closeddoor", loc), loc, false, true, false);
        open = ImageHandler.getImageIcon("opendoor", loc);
        closed = ImageHandler.getImageIcon("closeddoor", loc);
        locked = ImageHandler.getImageIcon("lockeddoor", loc);
        isLocked = lock;
        if(lock) treadable = false;
        else flammable = true;
    }
    
    
    
    public void unlock(){
        treadable = true;
        flammable = true;
        isLocked = false;
        setIcon(closed);
    }
    
    public void open(){
        isOpen = true;
        setIcon(open);
    }
    
    public void close(){
        isOpen = false;
        setIcon(closed);
    }
    
}
