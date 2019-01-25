
package animation;

import gui.Window;
import gui.mainToolbox.Pacemaker;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import listeners.AnimationListener;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 */
public abstract class FramedAnimation extends TrackableAnimation
        implements TickedAnimation{
    
    private final static long serialVersionUID = 174192632;
    
    public transient ImageIcon[] frames;
    protected int currentFrame = 0;
    protected double currentTicks, ticksPerFrame;
    protected final double maxTicks;
    
    protected FramedAnimation(ImageIcon[] f, double delay, AnimationListener li){
        super(li);
        frames = f;
        maxTicks = delay;
        try{
            ticksPerFrame = Window.main.pacemaker.getDelay();
        }catch(NullPointerException e){
            Pacemaker.registerWaitingAnimation(this);
        }
    }
    
    /**
     * Draws the current frame onto the given graphics.
     * @param g The graphics to draw on.
     * @param x The top left x.
     * @param y The top left y.
     */
    @Override
    public void animate(Graphics2D g, int x, int y){
        recalc();
        g.drawImage(frames[currentFrame].getImage(), x, y, null);
    }

    /**
     * Recalculates the frame number.
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
                    listener.animationDone(this);
                }
            }
        }
    }
    
    /**
     * Retrieves the current ImageIcon.
     * @return
     */
    public ImageIcon getCurrentIcon(){
        return frames[currentFrame];
    }
    
    /**
     * Flips all frames of this animation along the x axis.
     * @return The mirrored animation.
     */
    protected ImageIcon[] getMirroredIcons(){
        ImageIcon[] ret = new ImageIcon[frames.length];
        for(int n=0;n<frames.length;n++)
            ret[n] = ImageUtils.mirror(frames[n]);
        return ret;
    }
    
    abstract FramedAnimation mirror();
    
    /**
     * Sets the speed of this Animation.
     * @param tpf The amount of pacemaker ticks to wait per frame.
     */
    @Override
    public void setTicksPerFrame(double tpf){
        ticksPerFrame = tpf;
    }
    
}
