
package animation;

import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 */
public interface GameObjectAnimator extends Serializable{
    public void animate(Graphics g, int x, int y);
    public void switchTo(String name);
    public void switchAndBack(String name);
    public void switchFade(String name);
}
