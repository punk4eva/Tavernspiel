
package gui;

import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 */
public interface Viewable{
    List<Screen> getScreens();
    List<Screen> getScreenList();
    void paint(Graphics g);
}
