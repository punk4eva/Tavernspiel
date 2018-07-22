
package animation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import level.Location;
import listeners.AnimationListener;
import logic.ImageHandler;
import logic.mementoes.IconPointer;

/**
 *
 * @author Adam Whittaker
 * 
 * An Animation of a still image redesigned to make it more efficient.
 */
public class StillAnimation implements Animation{
    
    public transient ImageIcon image;
    private IconPointer pointer;
    private String loc;
    
    /**
     * Creates a still image Animation.
     * @param f The pointer to the Icon
     * @param l The Location.
     */
    public StillAnimation(Dimension f, Location l){
        image = ImageHandler.getImage(f, l);
        pointer = new IconPointer(f);
        loc = l.name;
    }
    
    @Override
    public void animate(Graphics g, int x, int y){
        g.drawImage(image.getImage(), x, y, null);
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        image = pointer.getIcon(Location.locationMap.get(loc));
    }
    
}
