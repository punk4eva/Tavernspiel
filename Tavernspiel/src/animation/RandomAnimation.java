
package animation;

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import listeners.AnimationListener;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class RandomAnimation extends Animation{
    
    private final Distribution skipChance;

    public RandomAnimation(ImageIcon[] f, int d, Distribution s){
        super(f, d);
        skipChance = s;
    }
    
    public RandomAnimation(Animation a, Distribution s){
        super(a.frames, (int)a.maxTicks);
        skipChance = s;
    }
    
    @Override
    protected void recalc(){
        currentTicks += ticksPerFrame;
        while(currentTicks>maxTicks){
            currentTicks -= maxTicks;
            if(!skipChance.chance()) currentFrame++;
            if(currentFrame>=frames.length){
                currentFrame = 0;
                if(listener!=null) listener.done(this);
            }
        }
    }
    
    /**
     * @deprecated 
     */
    @Override
    public void changeListener(AnimationListener l){
        throw new UnsupportedOperationException("Trying to change listener of RandomAnimation.");
    }
    
}
