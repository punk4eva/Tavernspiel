
package animation;

import gui.Window;
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
    protected int currentFrame = 0;
    protected AnimationListener listener;
    protected double currentTicks;
    protected final double maxTicks, ticksPerFrame;
    public boolean done = false;
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     */
    public Animation(ImageIcon[] f){
        frames = f;
        ticksPerFrame = Window.main.pacemaker.getDelay();
        maxTicks = 5;
    }
    
    /**
     * Creates an Animation from the given frames.
     * @param f The frames.
     * @param delay The delay between each frame.
     */
    public Animation(ImageIcon[] f, int delay){
        frames = f;
        ticksPerFrame = Window.main.pacemaker.getDelay();
        maxTicks = delay;
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
        ticksPerFrame = Window.main.pacemaker.getDelay();
        maxTicks = 5;
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
        ticksPerFrame = Window.main.pacemaker.getDelay();
        listener = al;
        maxTicks = d;
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
        recalc();
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

    /**
     * Recalculates the current frame.
     * Should be called in implementations of the animate method.
     */
    protected void recalc(){
        currentTicks += ticksPerFrame;
        while(currentTicks>maxTicks){
            currentTicks -= maxTicks;
            currentFrame++;
            if(currentFrame>=frames.length){
                currentFrame = 0;
                if(listener!=null){
                    done = true;
                    listener.done(this);
                }
            }
        }
    }
    
    /**
     * Gives the ImageIcon of the current frame.
     * Should only be used when scaling animations.
     * @return The current frame.
     * @see animate
     */
    public ImageIcon getCurrentIcon(){
        return frames[currentFrame];
    }
    
}
