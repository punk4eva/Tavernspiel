
package animation;

import gui.MainClass;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import listeners.AnimationListener;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class RandomAnimation extends Animation{
    
    private final int delayError;
    private double currentDelay;

    public RandomAnimation(ImageIcon[] f, double d, int delayE){
        super(f, d);
        delayError = delayE;
        currentDelay = Distribution.randomDouble(delay-delayError, delay+delayError);
    }
    
    public RandomAnimation(Animation a, int delayE){
        super(a.frames, a.delay);
        delayError = delayE;
        currentDelay = Distribution.randomDouble(delay-delayError, delay+delayError);
    }
    
    @Override
    public void animate(Graphics g, int x, int y){
        int m = (int)((MainClass.frameNumber/currentDelay)%frames.length);
        if(MainClass.frameNumber%100==0) currentDelay = Distribution.randomDouble(delay-delayError, delay+delayError);
        g.drawImage(frames[m].getImage(), x, y, null);
    }
    
    /**
     * @deprecated 
     */
    @Override
    public void changeListener(AnimationListener l){
        throw new UnsupportedOperationException("Trying to change listener of RandomAnimation.");
    }
    
}
