
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
        super(a.frames, a.timer.getDelay());
        skipChance = s;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(skipChance.chance()) return;
        currentFrame++;
        if(currentFrame>=frames.length){
            currentFrame = 0;
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
