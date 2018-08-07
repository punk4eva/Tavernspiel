
package animation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.ImageIcon;
import listeners.AnimationListener;

/**
 *
 * @author Adam Whittaker
 */
public class SerialAnimation extends FramedAnimation{
    
    private final static long serialVersionUID = 170000032;
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     */
    public SerialAnimation(ImageIcon[] f){
        super(f, 5, null);
    }
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param delay The delay between each frame.
     */
    public SerialAnimation(ImageIcon[] f, double delay){
        super(f, delay, null);
    }
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param d The delay between each frame.
     * @param al The listener that is interested in when this animation finishes
     * a cycle.
     */
    public SerialAnimation(ImageIcon[] f, double d, AnimationListener al){
        super(f, d, al);
    }
    
    @Override
    public SerialAnimation mirror(){
        return new SerialAnimation(getMirroredIcons(), maxTicks, listener);
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        frames = (ImageIcon[]) in.readObject();
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeObject(frames);
    }
    
}
