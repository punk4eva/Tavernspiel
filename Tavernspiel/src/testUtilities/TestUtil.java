
package testUtilities;

import animation.DrawnAnimation;
import gui.Window;
import gui.mainToolbox.Main;
import gui.mainToolbox.MouseInterpreter;
import java.awt.Color;
import java.awt.Graphics;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
@Unfinished("Remove later")
public class TestUtil{
    
    private static Graphics g;
    
    public static void setGraphics(Graphics g_){
        g = g_;
    }
    
    public static void pause(long mil){
        try{
            Window.main.pacemaker.stop();
            Thread.sleep(1000);
            Window.main.pacemaker.start();
        }catch(InterruptedException ex){
            System.err.println("Interrupted exception");
            ex.printStackTrace(Main.exceptionStream);
        }
    }
    
    public static void drawPixelLine(int x1, int y1, int x2, int y2, Color c){
        g.setColor(c);
        g.drawLine(x1, y1, x2, y2);
    }
    
    public static void drawTileLine(int x1, int y1, int x2, int y2, Color c){
        Integer[] c1 = MouseInterpreter.tileToPixel(x1, y1),
            c2 = MouseInterpreter.tileToPixel(x2, y2);
        drawPixelLine(c1[0], c1[1], c2[0], c2[1], c);
    }
    
    public static void drawPoint(int x, int y, Color c){
        Integer[] c1 = MouseInterpreter.tileToPixel(x, y);
        g.setColor(c);
        g.fillOval(c1[0]-4, c1[1]-4, 8, 8);
    }
    
    public static void animateTileLine(int mil, int x1, int y1, int x2, int y2, Color c){
        Main.animator.addAnimation(new DrawnAnimation(mil, Main.animator){
            @Override
            public void animate(Graphics g, int x, int y){
                recalc();
                drawTileLine(x1, y1, x2, y2, c);
            }
        });
    }
    
    public static void animatePoint(int mil, int x1, int y1, Color c){
        Main.animator.addAnimation(new DrawnAnimation(mil, Main.animator){
            @Override
            public void animate(Graphics g, int x, int y){
                recalc();
                drawPoint(x1, y1, c);
            }
        });
    }
    
}
