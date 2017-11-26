
package guiUtils;

import gui.Screen;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import listeners.ScreenListener;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 */
public class CCheckbox extends Screen implements CComponent{
    
    private Boolean on = false;
    private final int width, height;
    
    public CCheckbox(String n, int TLX, int TLY, ScreenListener li){
        super(n, TLX, TLY, 24, 24, li);
        width = brx-tlx;
        height = bry-tly;
    }

    @Override
    public void setTopLeft(int x, int y){
        tlx = x;
        tly = y;
        brx = tlx + width;
        bry = tly + height;
    }

    @Override
    public void paint(Graphics g){
        g.setColor(ConstantFields.backColor);
        g.fill3DRect(tlx, tly, width, height, true);
        g.setColor(ConstantFields.frontColor);
        g.fill3DRect(tlx+2, tly+2, width-4, height-4, false);
        g.setColor(ConstantFields.textColor);
        g.drawString(name, tlx+30, tly+18);
        if(on) g.fillRect(tlx+6, tly+6, width-12, width-12);
        
    }

    @Override
    public int[] getBounds(){
        return new int[]{tlx, tly, brx, bry};
    }

    @Override
    public Screen[] getScreens(){
        return new Screen[]{this};
    }

    @Override
    public Object getValue(){
        return on;
    }
    
    @Override
    public void wasClicked(MouseEvent me){
        on = !on;
    }
    
}
