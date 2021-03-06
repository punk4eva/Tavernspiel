
package gui.mainToolbox;

import gui.HUD;
import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 * 
 * A strategy for creating a HUD style.
 */
public interface HUDStrategy{
    public List<Screen> getScreens();
    public void paint(Graphics g);
    public void resetBuffScreens(HUD hud);
}
