
package animation;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 * 
 * A GameObjectAnimator for a Gas.
 * 
 * @Unfinished Bad design: shouldn't depend on switch methods.
 */
public class GasAnimator implements GameObjectAnimator{
    
    private final TrackableAnimation animation;
    
    public GasAnimator(TrackableAnimation a){
        animation = a;
    }

    @Override
    public void animate(Graphics2D g, int x, int y){
        animation.animate(g, x, y);
    }

    @Override
    public void switchTo(String name){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void switchAndBack(String name){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void switchFade(String name){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
