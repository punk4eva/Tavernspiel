
package gui.utils;

import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import java.awt.Color;
import java.awt.Graphics;
import listeners.ScreenListener;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 */
public class CButton extends Screen implements CComponent{
    
    private final int padding;
    private final int width, height;
    private boolean selected = false;
    
    public CButton(String n, int TLX, int TLY, int pad, ScreenListener li){
        super(n, TLX, TLY, Main.WIDTH/3-2*pad, 36, li);
        padding = pad;
        width = brx-tlx;
        height = bry-tly;
    }

    @Override
    public final void paint(Graphics g){
        g.setColor(ConstantFields.frontColor);
        g.fill3DRect(tlx, tly, width, height, !selected);
        if(selected){
            g.setColor(Color.WHITE);
            g.fillRect(tlx, tly, width, 4);
            g.fillRect(tlx, tly+4, 4, height-8);
            g.fillRect(tlx, tly+height-4, width, 4);
            g.fillRect(tlx+width-4, tly+4, 4, height-8);
        }
        g.setColor(ConstantFields.textColor);
        g.drawString(name, 2*padding+tlx, tly+2*padding);
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
    public void setTopLeft(int x, int y){
        tlx = x;
        tly = y;
        brx = tlx + width;
        bry = tly + height;
    }

    @Override
    public Object getValue(){
        return null;
    }
    
    public void setSelected(boolean s){
        selected = s;
    }
    
}
