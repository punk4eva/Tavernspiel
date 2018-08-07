
package animation.assets;

import animation.DrawnAnimation;
import gui.mainToolbox.Main;
import gui.mainToolbox.MouseInterpreter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class WaterStepAnimation extends DrawnAnimation{
    
    private final static long serialVersionUID = 321147;
    
    private static final Color COLOR = new Color(100,100,220,150);
    private final int x, y;
    private int diameter = 1;

    public WaterStepAnimation(int tx, int ty){
        super(30, Main.animator);
        x = tx;
        y = ty;
    }

    @Override
    public void animate(Graphics g, int __x, int __y){
        recalc();
        g.setColor(COLOR);
        int offset = diameter/2;
        Integer c[] = MouseInterpreter.tileToPixel(x, y);
        ((Graphics2D)g).drawOval(c[0]-offset, c[1]-offset, diameter, diameter);
    }
    
    @Override
    protected void recalc(){
        currentTicks += ticksPerFrame;
        while(currentTicks>maxTicks){
            currentTicks-=maxTicks;
            diameter++;
            if(diameter>=15){
                done = true;
                if(listener!=null) listener.done(this);
            }
        }
    }
    
}
