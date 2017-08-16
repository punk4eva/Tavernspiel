
package animation;

import gui.MainClass;
import java.awt.Graphics;
import java.awt.Image;
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
    protected long offset = 0;
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     */
    public Animation(Image[] f){
        frames = f;
        MainClass.addAnimation(this);
    }
    
    /**
     * Creates an Animation from the given frames and adds an Animation listener.
     * @param f The frames.
     * @param al The listener that is interested in when this animation finishes
     * a cycle.
     */
    public Animation(Image[] f, AnimationListener al){
        frames = f;
        listener = al;
        MainClass.addAnimation(this);
    }
    
    /**
     * Changes the listener of this animation.
     * @param l The new listener.
     */
    public void changeListener(AnimationListener l){
        listener = l;
    }
    
    /**
     * Draws the current frame onto the given graphics.
     * @param g The graphics to draw on.
     * @param x The top left x.
     * @param y The top left y.
     */
    public void animate(Graphics g, int x, int y){
        int m = (int)((MainClass.frameNumber-offset)%frames.length);
        double z = MainClass.getZoom();
        if(z==1.0) g.drawImage(frames[m], x, y, null);
        else g.drawImage(frames[m].getScaledInstance((int)(16*z),(int)(16*z),0), x, y, null);
        if(x==0&&listener!=null) listener.done();
    }
    
    /**
     * Adds shaders (overlay) to this animation.
     * @param shaderString The regular expression type string indicating where
     * shaders come from or if it is a special shader.
     * @param loc The location whose tileset to use.
     */
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
