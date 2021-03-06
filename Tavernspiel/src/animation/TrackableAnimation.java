
package animation;

import listeners.AnimationListener;

/**
 *
 * @author Adam Whittaker
 */
public abstract class TrackableAnimation implements Animation{
    
    private final static long serialVersionUID = 17498092;
    
    protected AnimationListener listener;
    public boolean done = false;
    
    protected TrackableAnimation(AnimationListener li){
        listener = li;
    }
    
    /**
     * Changes the listener of this animation.
     * @param li The new listener.
     */
    public final void changeListener(AnimationListener li){
        listener = li;
    }
    
}
