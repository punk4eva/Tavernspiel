
package animation;

import gui.Window;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import listeners.AnimationListener;

/**
 *
 * @author Adam Whittaker
 */
public abstract class TrackableAnimation implements Animation{
    
    protected AnimationListener listener;
    public boolean done = false;
    
    protected TrackableAnimation(AnimationListener li){
        listener = li;
    }
    
    /**
     * Changes the listener of this animation.
     * @param li The new listener.
     */
    public void changeListener(AnimationListener li){
        listener = li;
    }
    
}
