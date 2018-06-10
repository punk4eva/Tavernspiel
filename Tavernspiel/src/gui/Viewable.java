
package gui;

import gui.mainToolbox.Screen;
import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 */
public interface Viewable{
    List<Screen> getScreens();
    void paint(Graphics g);
}
