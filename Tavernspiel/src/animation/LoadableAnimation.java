
package animation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import listeners.AnimationListener;

/**
 *
 * @author Adam Whittaker
 */
public class LoadableAnimation extends FramedAnimation{
    
    public Supplier<ImageIcon[]> loader;
    
    /**
     * Creates a new instance.
     * @param lo The loader.
     * @param delay The frame delay.
     * @param li The Listener.
     */
    public LoadableAnimation(Supplier<ImageIcon[]> lo, double delay, AnimationListener li){
        super(lo.get(), delay, li);
        loader = lo;
    }
    
    @Override
    public LoadableAnimation mirror(){
        return new LoadableAnimation(() -> getMirroredIcons(), maxTicks, listener);
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        frames = loader.get();
    }
    
}
