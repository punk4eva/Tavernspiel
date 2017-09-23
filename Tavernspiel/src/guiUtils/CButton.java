
package guiUtils;

import gui.MainClass;
import gui.Screen;
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
    
    public CButton(String n, int TLX, int TLY, int pad, ScreenListener li){
        super(n, TLX, TLY, MainClass.WIDTH/3-2*pad, 36, li);
        padding = pad;
        width = brx-tlx;
        height = bry-tly;
    }

    @Override
    public void paint(Graphics g){
        g.setColor(ConstantFields.frontColor);
        g.fill3DRect(tlx, tly, width, height, true);
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
    
}
