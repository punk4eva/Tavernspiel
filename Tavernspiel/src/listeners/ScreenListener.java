
package listeners;

import gui.mainToolbox.Screen.ScreenEvent;

/**
 *
 * @author Adam Whittaker
 * 
 * An Object that needs to know about which Screen has been clicked.
 */
public interface ScreenListener{
    void screenClicked(ScreenEvent name);
}
