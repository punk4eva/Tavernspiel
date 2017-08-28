
package gui;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import listeners.ScreenListener;

/**
 *
 * @author Adam Whittaker
 */
public class Screen implements Serializable{
    
    protected final String name;
    protected final int tlx, tly, brx, bry;
    private ScreenListener listener;
    
    public Screen(String n, int TLX, int TLY, int width, int height, ScreenListener li){
        listener = li;
        name = n;
        tlx = TLX;
        tly = TLY;
        brx = tlx+width;
        bry = tly+height;
    }

    public ScreenListener getListener(){
        return listener;
    }
    
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
    
    public String getName(){
        return name;
    }
    
    public void changeScreenListener(ScreenListener sl){
        listener = sl;
    }
    
    public void wasClicked(MouseEvent me){
        listener.screenClicked(new ScreenEvent(this, me));
    }

    public boolean withinBounds(int x, int y){
        return y>=tly&&y<=bry&&x>=tlx&&x<=brx;
    }
    
}
