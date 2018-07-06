
package gui.mainToolbox;

import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 */
public interface HUDStrategy{
    public List<Screen> getScreens();
    public void paint(Graphics g);
}
