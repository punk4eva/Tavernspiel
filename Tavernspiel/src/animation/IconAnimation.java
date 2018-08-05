
package animation;

import java.io.IOException;
import java.io.ObjectInputStream;
import level.Location;
import listeners.AnimationListener;
import logic.mementoes.AnimationMemento;

/**
 *
 * @author Adam Whittaker
 */
public class IconAnimation extends FramedAnimation{
    
    private final AnimationMemento memento;
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param loc The Location.
     */
    public IconAnimation(String[] f, Location loc){
        super(null, 5, null);
        memento = new AnimationMemento(f, loc);
        frames = memento.getIcons();
    }
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param loc The Location.
     * @param delay The delay between each frame.
     */
    public IconAnimation(String[] f, Location loc, int delay){
        super(null, delay, null);
        memento = new AnimationMemento(f, loc);
        frames = memento.getIcons();
    }
    
    /**
     * Creates an Animation from the given frames and adds an Animation listener.
     * @param f The frames.
     * @param loc The Location.
     * @param al The listener that is interested in when this animation finishes
     * a cycle.
     */
    public IconAnimation(String[] f, Location loc, AnimationListener al){
        super(null, 5, al);
        memento = new AnimationMemento(f, loc);
        frames = memento.getIcons();
    }
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param loc The Location.
     * @param d The delay between each frame.
     * @param al The listener that is interested in when this animation finishes
     * a cycle.
     */
    public IconAnimation(String[] f, Location loc, int d, AnimationListener al){
        super(null, d, al);
        memento = new AnimationMemento(f, loc);
        frames = memento.getIcons();
    }
    
    @Override
    public FramedAnimation mirror(){
        throw new UnsupportedOperationException("Trying to mirror IconAnimation");
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        frames = memento.getIcons();
    }
    
}
