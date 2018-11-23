
package animation;

import java.awt.Graphics2D;
import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 * 
 * Represents animation.
 */
public interface Animation extends Serializable{
    /**
     * Draws the current frame onto the given graphics.
     * @param g The graphics to draw on.
     * @param x The top left x.
     * @param y The top left y.
     */
    public void animate(Graphics2D g, int x, int y);
}
