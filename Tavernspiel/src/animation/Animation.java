
package animation;

import gui.MainClass;
import java.awt.Graphics;
import java.io.Serializable;
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
public class Animation implements Serializable{
    
    private final static long serialVersionUID = -1172489372;
    
    public final ImageIcon[] frames;
    private AnimationListener listener;
    protected long offset = 0;
    public double delay = 1;
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     */
    public Animation(ImageIcon[] f){
        frames = f;
        MainClass.addAnimation(this);
    }
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param d The delay between each frame.
     */
    public Animation(ImageIcon[] f, double d){
        frames = f;
        delay = d;
        MainClass.addAnimation(this);
    }
    
    /**
     * Creates an Animation from the given frames and adds an Animation listener.
     * @param f The frames.
     * @param al The listener that is interested in when this animation finishes
     * a cycle.
     */
    public Animation(ImageIcon[] f, AnimationListener al){
        frames = f;
        listener = al;
        MainClass.addAnimation(this);
    }
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param d The delay between each frame.
     * @param al The listener that is interested in when this animation finishes
     * a cycle.
     */
    public Animation(ImageIcon[] f, double d, AnimationListener al){
        frames = f;
        delay = d;
        listener = al;
        MainClass.addAnimation(this);
    }
    
    /**
     * Creates a still image Animation.
     * @param icon The Icon.
     * @deprecated
     * Only for use with StillAnimation
     */
    protected Animation(ImageIcon icon){
        frames = new ImageIcon[]{icon};
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
        int m = (int)((MainClass.frameNumber/delay-offset)%frames.length);
        double z = MainClass.getZoom();
        if(z==1.0) g.drawImage(frames[m].getImage(), x, y, null);
        else g.drawImage(frames[m].getImage().getScaledInstance((int)(16*z),(int)(16*z),0), x, y, null);
        if(m==frames.length-1&&listener!=null) listener.done();
    }
    
    /**
     * Adds shaders (overlay) to this animation.
     * @param shaderString The regular expression type string indicating where
     * shaders come from or if it is a special shader.
     * @param loc The location whose tileset to use.
     */
    public void addShaders(String shaderString, Location loc){
        if(shaderString.equals("well") || shaderString.equals("alchemypot")){
            ImageIcon shader = ImageHandler.getImage(shaderString, loc);
            for(int n=0;n<frames.length;n++){
                frames[n] = ImageHandler.combineIcons(frames[n], shader);
            }
        }else{
            ImageIcon shader = ImageHandler.getImage("shader" + shaderString, loc);
            for(int n=0;n<frames.length;n++){
                frames[n] = ImageHandler.combineIcons(frames[n], shader);
            }
        }
    }
    
}
