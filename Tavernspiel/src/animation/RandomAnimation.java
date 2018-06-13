
package animation;

import javax.swing.ImageIcon;
import listeners.AnimationListener;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents an animation which randomly changes frame rather than
 * having a set framerate.
 */
public class RandomAnimation extends Animation{
    
    private final Distribution skipChance;

    /**
     * Creates a new instance.
     * @param f The frames.
     * @param d The delay between frames.
     * @param s The chance that a given frame increment will be skipped.
     */
    public RandomAnimation(ImageIcon[] f, int d, Distribution s){
        super(f, d);
        skipChance = s;
    }
    
    /**
     * Creates a random animation from a given one.
     * @param a The Animation.
     * @param s The skip chance.
     */
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
     * A RandomAnimation should not need a listener.
     * @unfinished Remove later.
     * @deprecated 
     */
    @Override
    public void changeListener(AnimationListener l){
        throw new UnsupportedOperationException("Trying to change listener of RandomAnimation.");
    }
    
}
