
package gui;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public interface Viewable{
    ArrayList<Screen> getScreens();
    ArrayList<Screen> getScreenList();
    void paint(Graphics g);
}
