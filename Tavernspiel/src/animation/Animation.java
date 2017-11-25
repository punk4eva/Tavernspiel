
package animation;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import level.Location;
import listeners.AnimationListener;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles animation.
 */
public class Animation implements Serializable, ActionListener{
    
    private final static long serialVersionUID = -1172489372;
    
    public final ImageIcon[] frames;
    protected int currentFrame = 0;
    private AnimationListener listener;
    protected Timer timer;
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     */
    public Animation(ImageIcon[] f){
        frames = f;
        timer = new Timer(5, this);
    }
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param delay The delay between each frame.
     */
    public Animation(ImageIcon[] f, int delay){
        frames = f;
        timer = new Timer(delay, this);
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
        timer = new Timer(5, this);
    }
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param d The delay between each frame.
     * @param al The listener that is interested in when this animation finishes
     * a cycle.
     */
    public Animation(ImageIcon[] f, int d, AnimationListener al){
        frames = f;
        timer = new Timer(d, this);
        listener = al;
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
        g.drawImage(frames[currentFrame].getImage(), x, y, null);
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

    @Override
    public void actionPerformed(ActionEvent ae){
        currentFrame++;
        if(currentFrame>=frames.length){
            currentFrame = 0;
            if(listener!=null) listener.done();
        }
    }
    
    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }
    
}
