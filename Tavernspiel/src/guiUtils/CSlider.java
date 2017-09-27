
package guiUtils;

import gui.MainClass;
import gui.Screen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import listeners.ScreenListener;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class CSlider implements CComponent, ScreenListener{
    
    protected final String name;
    private int TLX, TLY, BRX, BRY;
    private final int min, max, increment, numberSlots;
    private final boolean discreet;
    private int value = Integer.MIN_VALUE;
    private final int width, height;
    private final CSliderHandle handle;
    transient static final ImageIcon handleIcon = new ImageIcon("graphics/gui/csliderhandle.png");
    transient static final Color SLIDER_COLOR1 = new Color(160, 160, 160);
    transient static final Color SLIDER_COLOR2 = new Color(104, 104, 104);
    
    
    public CSlider(String n, int tlx, int tly, int min_, int max_, int inc){
        name = n;
        TLX = tlx;
        TLY = tly;
        BRX = TLX+MainClass.WIDTH/3-16;
        BRY = TLY+36;
        min = min_;
        max = max_;
        increment = inc;
        numberSlots = 1 + (max-min)/increment;
        discreet = numberSlots<6;
        width = BRX-TLX;
        height = BRY-TLY;
        handle = new CSliderHandle(0, this);
    }
    
    public CSlider(String n, int tlx, int tly, int min_, int max_, int inc, int position){
        name = n;
        TLX = tlx;
        TLY = tly;
        BRX = TLX+MainClass.WIDTH/3-16;
        BRY = TLY+36;
        min = min_;
        max = max_;
        increment = inc;
        numberSlots = 1 + (max-min)/increment;
        discreet = numberSlots<6;
        width = BRX-TLX;
        height = BRY-TLY;
        handle = new CSliderHandle(position, this);
    }

    
    public class CSliderHandle extends Screen implements CComponent{
        
        int position;
        private boolean dragging = false;
        private int xOfDrag=-1;

        CSliderHandle(int pos, ScreenListener list){
            super("CSliderHandle", TLX + pos*(BRX-TLX)/numberSlots - 9, TLY+4, 24, 28, list);
            position = pos;
        }
        
        private int getX(){
            int rounded = Utils.roundToClosest(12+tlx-TLX, (BRX-TLX)/(numberSlots), 1.0-0.05*(double)numberSlots);
            return rounded+TLX-12;
        }
        
        private void updatePosition(int x){
            float ax = x-TLX;
            position = Math.round(ax/(numberSlots-1));
        }

        @Override
        public void paint(Graphics g){
            g.drawImage(handleIcon.getImage(), tlx+6, tly, null);
        }

        @Override
        public int[] getBounds(){
            return new int[]{tlx, tly, brx, bry};
        }
        
        @Override
        public void wasClicked(MouseEvent me){
            switch(me.getID()){
                case MouseEvent.MOUSE_DRAGGED:
                    if(!dragging){
                        dragging = true;
                        xOfDrag = me.getX() - tlx;        
                    }
                    tlx = me.getX() - xOfDrag;
                    if(tlx>BRX+12) tlx = BRX+12;
                    else if(tlx<TLX-9) tlx = TLX-9;
                    if(discreet) tlx = getX();
                    updatePosition(tlx);
                    break;
                case MouseEvent.MOUSE_RELEASED:
                    if(dragging){
                        dragging = false;
                        xOfDrag = -1;
                        listener.screenClicked(null);
                    }
            }
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

    @Override
    public void paint(Graphics g){
        if(discreet){
            g.setColor(Color.white);
            int inc = (BRX-TLX)/numberSlots;
            for(int n=TLX;n<=BRX;n+=inc){
                g.drawLine(n, TLY+10, n, TLY+26);
            }
        }
        g.setColor(SLIDER_COLOR2);
        g.fill3DRect(TLX, TLY, 8, height, true);
        g.fill3DRect(BRX-8, TLY, 8, height, true);
        g.fillRect(TLX, TLY+15, width, height-30);
        g.setColor(SLIDER_COLOR1);
        g.fillRect(TLX, TLY+17, width, height-34);
        handle.paint(g);
    }

    @Override
    public int[] getBounds(){
        return new int[]{TLX, TLY, BRX, BRY};
    }
    
    @Override
    public void screenClicked(Screen.ScreenEvent name){
        for(int n=0;n<handle.position;n++){
            value += increment;
        }
        value += min;
    }
    
    @Override
    public void setTopLeft(int x, int y){
        TLX = x;
        TLY = y;
        BRX = TLX+width;
        BRY = TLY+height;
        handle.setTopLeft(TLX + handle.position*(BRX-TLX)/numberSlots - 9, TLY+4);
    }
    
    @Override
    public Screen[] getScreens(){
        return new Screen[]{handle};
    }
    
    public int getValue(){
        return value;
    }
    
}
