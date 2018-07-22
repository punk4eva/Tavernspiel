
package animation;

import java.awt.Graphics;

/**
 *
 * @author Adam Whittaker
 */
public class GasAnimator implements GameObjectAnimator{
    
    private final Animation animation;
    
    public GasAnimator(Animation a){
        animation = a;
    }

    @Override
    public void animate(Graphics g, int x, int y){
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
