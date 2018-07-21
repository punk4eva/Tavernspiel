
package animation;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class GrassAnimation extends DrawnAnimation{
        
    private final int[][] pixels;
    private final ImageIcon icon;
    protected int R, G, B;
    protected int minR, minG, minB, maxR, maxG, maxB;
    protected boolean rUP = true, gUP = true, bUP = false;

    /**
     * Creates a new instance.
     * @param p The array of pixel coordinates to recolor.
     * @param i The ImageIcon.
     * @param r The starting red value.
     * @param g The starting red value.
     * @param b The starting red value.
     * @param v The array of min/max RGB values.
     */
    public GrassAnimation(int[][] p, ImageIcon i, int r, int g, int b, int... v){
        super(8800, null);
        pixels = p;
        icon = i;
        R = r;
        G = g;
        B = b;
        minR = v[0];
        minG = v[1];
        minB = v[2];
        maxR = v[3];
        maxG = v[4];
        maxB = v[5];
    }

    @Override
    public void animate(Graphics g, int x, int y){
        g.drawImage(icon.getImage(), x, y, null);
        g.setColor(new Color(R, G, B));
        for(int[] pixel : pixels)
            g.drawRect(pixel[0]+x, pixel[1]+y, 0, 0);
        recalc();
    }
    
    @Override
    protected void recalc(){
        currentTicks += ticksPerFrame;
        while(currentTicks>maxTicks){
            currentTicks -= maxTicks;
            tickHue();
        }
    }
    
    /**
     * Adjusts this Animation's Color.
     */
    protected void tickHue(){
        if(rUP){
            R++;
            if(R>=maxR) rUP = false;
        }else{
            R--;
            if(R<=minR) rUP = true;
        }
        if(gUP){
            G++;
            if(G>=maxG) gUP = false;
        }else{
            G--;
            if(G<=minG) gUP = true;
        }
        if(bUP){
            B++;
            if(B>=maxB) bUP = false;
        }else{
            B--;
            if(B<=minB) bUP = true;
        }
        
    }
    
    /**
     * Carries this Animation on from a previous one.
     * @param g The previous Animation.
     */
    public void startFrom(GrassAnimation g){
        R = g.R;
        G = g.G;
        B = g.B;
        rUP = g.rUP;
        gUP = g.gUP;
        bUP = g.bUP;
    }

}
