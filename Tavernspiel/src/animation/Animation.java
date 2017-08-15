
package animation;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import level.Location;
import listeners.AnimationListener;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles animation.
 */
public class Animation{
    
    public final Image[] frames;
    private AnimationListener listener;
    
    public Animation(Image[] f){
        frames = f;
    }
    
    public Animation(Image[] f, AnimationListener al){
        frames = f;
        listener = al;
    }
    
    public void changeListener(AnimationListener l){
        listener = l;
    }
    
    public void animate(Graphics g, int x, int y, long fn){
        int m = (int)(fn%frames.length);
        g.drawImage(frames[m], x, y, null);
        if(x==0&&listener!=null) listener.done();
    }
    
    public void addShaders(String shaderString, Location loc){
        if(shaderString.equals("well") || shaderString.equals("alchemypot")){
            Image shader = ImageHandler.getImage(shaderString, loc);
            for(int n=0;n<frames.length;n++){
                frames[n] = ImageHandler.combineIcons(frames[n], shader);
            }
        }else{
            Image shader = ImageHandler.getImage("shader" + shaderString, loc);
            for(int n=0;n<frames.length;n++){
                frames[n] = ImageHandler.combineIcons(frames[n], shader);
            }
        }
    }
    
}
