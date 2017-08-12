
package gui;

import listeners.ScreenListener;

/**
 *
 * @author Adam Whittaker
 */
public class Screen{
    
    protected final String name;
    protected final int tlx, tly, brx, bry;
    private ScreenListener listener;
    
    public Screen(String n, int TLX, int TLY, int BRX, int BRY){
        name = n;
        tlx = TLX;
        tly = TLY;
        brx = BRX;
        bry = BRY;
    }
    
    public void changeScreenListener(ScreenListener sl){
        listener = sl;
    }
    
    public void wasClicked(){
        listener.screenClicked(name);
    }

    public boolean withinBounds(int x, int y){
        return y>=tly&&y<=bry&&x>=tlx&&x<=brx;
    }
    
}
