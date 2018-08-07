
package animation;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.function.Supplier;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class LoadableStillAnimation implements Animation{
    
    private final static long serialVersionUID = 892733132;
    
    public transient ImageIcon image;
    private final Supplier<ImageIcon> loader;
    
    /**
     * Creates a new instance.
     * @param lo The loader for the Image. Make sure to co-implement the 
     * Serializable interface.
     */
    public LoadableStillAnimation(Supplier<ImageIcon> lo){
        loader = lo;
        image = loader.get();
    }

    @Override
    public void animate(Graphics g, int x, int y){
        g.drawImage(image.getImage(), x, y, null);
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        image = loader.get();
    }
    
}
