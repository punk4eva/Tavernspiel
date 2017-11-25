
package animation;

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
    
    /**
     * @deprecated
     */
    @Override
    public void stop(){
        throw new IllegalStateException("Trying to stop still Animation.");
    }
    
    /**
     * @deprecated
     */
    @Override
    public void start(){
        throw new IllegalStateException("Trying to start still Animation.");
    }
    
    @Override
    public void animate(Graphics g, int x, int y){
        g.drawImage(frames[0].getImage(), x, y, null);
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
