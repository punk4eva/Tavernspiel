
package gui.mainToolbox;

import gui.utils.CComponent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import listeners.ScreenListener;

/**
 *
 * @author Adam Whittaker
 * 
 * An Object that represents the region where something can be clicked, and tells
 * the ScreenListener.
 */
public class Screen implements Serializable{
    
    private final static long serialVersionUID = -1823822708;
    
    protected final String name;
    protected int tlx, tly, brx, bry;
    protected transient ScreenListener listener;
    
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
     * Gets the ScreenListener associated with this Object.
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
        public final int x, y;
        private Object[] data;
        
        public ScreenEvent(Screen sc, MouseEvent m){
            name = sc.name;
            me = m;
            Integer[] c = MouseInterpreter.pixelToTile(m.getX(), m.getY());
            x = c[0];
            y = c[1];
        }
        
        public ScreenEvent(String n){
            name = n;
            me = null;
            x = -1;
            y = -1;
        }
        
        public ScreenEvent(CComponent[] comps){
            data = new Object[comps.length];
            for(int n=0;n<comps.length;n++){
                data[n] = comps[n].getValue();
            }
            name = "Components";
            me = null;
            x = -1;
            y = -1;
        }
        
        public String getName(){
            return name;
        }
        
        public MouseEvent getMouseEvent(){
            return me;
        }
        
        public boolean hasData(){
            return data!=null;
        }
        
        public Object[] getData(){
            return data;
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
    
    /**
     * Resets the size and coordinates of this Screen.
     * @param tx the top left x
     * @param ty the top left y
     * @param w The width
     * @param h The height
     */
    public void reposition(int tx, int ty, int w, int h){
        tlx = tx;
        tly = ty;
        brx = tx+w;
        bry = ty+h;
    }
    
}
