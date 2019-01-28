
package gui;

import gui.mainToolbox.Screen;
import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 * 
 * A pop-up or other Object that can be put on the Canvas and clicked on.
 */
public interface Viewable{
    List<Screen> getScreens();
    void paint(Graphics g);
}
