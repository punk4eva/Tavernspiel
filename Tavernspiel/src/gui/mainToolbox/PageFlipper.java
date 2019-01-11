
package gui.mainToolbox;

import gui.pages.LoadingPage;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 * 
 * This class controls which page to display on-screen.
 */
public class PageFlipper{
    
    private final Main main;
    private Cursor cursor;
    
    /**
     * Creates an instance.
     * @param m
     */
    public PageFlipper(Main m){
        main = m;
        cursor = Toolkit.getDefaultToolkit().createCustomCursor(
            new ImageIcon("graphics\\gui\\cursors\\neutralCursor.png").getImage(), new Point(0, 0), "NEUTRAL CURSOR");
        m.setCursor(cursor);
    }
    
    /**
     * Sets the page.
     * @param name The name of the page.
     */
    public void setPage(String name){
        switch(name){
            case "loading": main.page = new LoadingPage();
                break;
            case "main": main.page = main;
                break;
        }
    }
    
}
