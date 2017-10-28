
package animation;

import gui.MainClass;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import level.Location;
import listeners.AnimationListener;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 * 
 * An Animation of a still image.
 * Redesigned to make it more efficient.
 */
public class StillAnimation extends Animation{
    
    /**
     * Creates a still image Animation.
     * @param icon
     */
    public StillAnimation(ImageIcon icon){
        super(icon);
    }
    
    /**
     * @deprecated
     */
    @Override
    public void changeListener(AnimationListener l){
        throw new IllegalStateException("Trying to call changeListener() for a "
                + "still Animation.");
    }
    
    @Override
    public void animate(Graphics g, int x, int y, double z){
        if(z==1.0) g.drawImage(frames[0].getImage(), x, y, null);
        else g.drawImage(frames[0].getImage().getScaledInstance((int)(16*z),(int)(16*z),0), x, y, null);
    }
    
    @Override
    public void addShaders(String shaderString, Location loc){
        if(shaderString.equals("well") || shaderString.equals("alchemypot")){
            ImageIcon shader = ImageHandler.getImage(shaderString, loc);
            frames[0] = ImageHandler.combineIcons(frames[0], shader);
        }else{
            ImageIcon shader = ImageHandler.getImage("shader" + shaderString, loc);
            frames[0] = ImageHandler.combineIcons(frames[0], shader);
        }
    }
    
}
