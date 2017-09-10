
package gui;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import listeners.ScreenListener;

/**
 *
 * @author Adam Whittaker
 * 
 * An Object that represents the region where something can be clicked, and tells
 * the ScreenListener
 */
public class Screen implements Serializable{
    
    private final static long serialVersionUID = -1823822708;
    
    protected final String name;
    protected final int tlx, tly, brx, bry;
    private ScreenListener listener;
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param TLX The top left x.
     * @param TLY The top left y.
     * @param width The width.
     * @param height The height.
     * @param li The ScreenListener.
     */
    public Screen(String n, int TLX, int TLY, int width, int height, ScreenListener li){
        listener = li;
        name = n;
        tlx = TLX;
        tly = TLY;
        brx = tlx+width;
        bry = tly+height;
    }

    /**
     * Gets the ScreenListener associated with this object.
     * @return The ScreenListener.
     */
    public ScreenListener getListener(){
        return listener;
    }
    
    /**
     * This class stores the MouseEvent that clicked the Screen
     */
    public static class ScreenEvent{
        
        private final String name;
        private final MouseEvent me;
        
        public ScreenEvent(Screen sc, MouseEvent m){
            name = sc.name;
            me = m;
        }
        
        public ScreenEvent(String n){
            name = n;
            me = null;
        }
        
        public String getName(){
            return name;
        }
        
        public MouseEvent getMouseEvent(){
            return me;
        }
        
    }
    
    /**
     * Gets the name of the Screen clicked.
     * @return The name as a String.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Changes the ScreenListener.
     * @param sl The new ScreenListener.
     */
    public void changeScreenListener(ScreenListener sl){
        listener = sl;
    }
    
    /**
     * Alerts the ScreenListener of a click.
     * @param me The MouseEvent that clicked the Screen.
     */
    public void wasClicked(MouseEvent me){
        listener.screenClicked(new ScreenEvent(this, me));
    }

    /**
     * Checks whether the given coordinates are within bounds of the Screen.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return true if they are, false if not.
     */
    public boolean withinBounds(int x, int y){
        return y>=tly&&y<=bry&&x>=tlx&&x<=brx;
    }
    
}
