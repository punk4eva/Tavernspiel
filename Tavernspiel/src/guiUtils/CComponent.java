
package guiUtils;

import gui.Screen;
import java.awt.Graphics;

/**
 *
 * @author Adam Whittaker
 */
public interface CComponent{

    public void setTopLeft(int x, int y);
    public void paint(Graphics g);
    public int[] getBounds();
    public Screen[] getScreens();
    
}
