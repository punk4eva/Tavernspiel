
package animation;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles animation.
 */
public class Animation{
    
    public Image[] frames;
    
    public Animation(ImageIcon[] f){
        frames = new BufferedImage[f.length];
        for(int n=0;n<f.length;n++){
            frames[n] = f[n].getImage();
        }
    }
    
    public void animate(Graphics g, int x, int y, long fn){
        g.drawImage(frames[(int)(fn%frames.length)], x, y, null);
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
