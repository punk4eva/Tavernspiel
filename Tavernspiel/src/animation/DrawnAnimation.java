
package animation;

import gui.Window;
import gui.mainToolbox.Pacemaker;
import listeners.AnimationListener;

/**
 *
 * @author Adam Whittaker
 * 
 * Represents an Animation that has been drawn.
 */
public abstract class DrawnAnimation extends TrackableAnimation 
        implements TickedAnimation{
    
    private static final long serialVersionUID = 831290;
    
    protected double currentTicks, ticksPerFrame;
    protected final double maxTicks;
    
    public DrawnAnimation(int time, AnimationListener al){
        super(al);
        try{
            ticksPerFrame = Window.main.pacemaker.getDelay();
        }catch(NullPointerException e){
            Pacemaker.registerWaitingAnimation(this);
        }
        maxTicks = time;
    }
    
    public DrawnAnimation(AnimationListener al){
        super(al);
        maxTicks = 1;
    }
    
    protected void recalc(){
        currentTicks += ticksPerFrame;
        if(currentTicks>maxTicks){
            done = true;
            if(listener!=null) listener.animationDone(this);
        }
    }
    
    @Override
    public void setTicksPerFrame(double tpf){
        ticksPerFrame = tpf;
    }
    
}
