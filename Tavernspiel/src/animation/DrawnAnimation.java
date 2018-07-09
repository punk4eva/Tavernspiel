
package animation;

import java.awt.Graphics;
import listeners.AnimationListener;

/**
 *
 * @author Adam Whittaker
 */
public abstract class DrawnAnimation extends Animation{
    
    public DrawnAnimation(int time, AnimationListener al){
        super(null, time, al);
    }
    
    @Override
    public abstract void animate(Graphics g, int x, int y);
    
    @Override
    protected void recalc(){
        currentTicks += ticksPerFrame;
        if(currentTicks>maxTicks){
            done = true;
            listener.done(this);
        }
    }
    
}
