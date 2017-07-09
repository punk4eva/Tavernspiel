
package tiles;

import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Door extends Tile{
    
    protected final ImageIcon open;
    protected final ImageIcon closed;
    protected final ImageIcon locked;
    protected boolean isLocked;
    protected boolean isOpen = false;
    
    public Door(Location loc){
        super("Door", ImageHandler.getImageIcon("closeddoor", loc));
        open = ImageHandler.getImageIcon("opendoor", loc);
        closed = ImageHandler.getImageIcon("closeddoor", loc);
        locked = ImageHandler.getImageIcon("lockeddoor", loc);
    }
    
    public Door(Location loc, boolean lock){
        super("Door", lock ?
                ImageHandler.getImageIcon("lockeddoor", loc) :
                ImageHandler.getImageIcon("closeddoor", loc));
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
