
package animation;

import gui.Window;
import listeners.AnimationListener;

/**
 *
 * @author Adam Whittaker
 * 
 * Represents an Animation that has been drawn.
 */
public abstract class DrawnAnimation extends TrackableAnimation{
    
    private static final long serialVersionUID = 831290;
    
    protected double currentTicks;
    protected final double maxTicks, ticksPerFrame;
    
    public DrawnAnimation(int time, AnimationListener al){
        super(al);
        ticksPerFrame = Window.main.pacemaker.getDelay();
        maxTicks = time;
    }
    
    protected void recalc(){
        currentTicks += ticksPerFrame;
        if(currentTicks>maxTicks){
            done = true;
            if(listener!=null) listener.done(this);
        }
    }
    
}
