
package animation;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import level.Location;
import logic.mementoes.IconPointer;

/**
 *
 * @author Adam Whittaker
 * 
 * An Animation of a still image redesigned to make it more efficient.
 */
public class StillAnimation implements Animation{
    
    private final static long serialVersionUID = 234893132;
    
    public transient ImageIcon image;
    private final IconPointer pointer;
    private final String loc;
    
    /**
     * Creates a still image Animation.
     * @param f The pointer to the Icon
     * @param l The Location.
     */
    public StillAnimation(String f, Location l){
        image = l.getImage(f);
        pointer = new IconPointer(f);
        loc = l.name;
    }
    
    @Override
    public void animate(Graphics2D g, int x, int y){
        g.drawImage(image.getImage(), x, y, null);
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        image = pointer.getIcon(Location.LOCATION_MAP.get(loc));
    }
    
}
