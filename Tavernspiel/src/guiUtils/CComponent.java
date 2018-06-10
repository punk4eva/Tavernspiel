
package guiUtils;

import gui.mainToolbox.Screen;
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
    public Object getValue();
    
}
