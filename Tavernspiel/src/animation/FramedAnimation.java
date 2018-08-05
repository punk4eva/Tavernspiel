
package animation;

import gui.Window;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import listeners.AnimationListener;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 */
public abstract class FramedAnimation extends TrackableAnimation{
    
    public transient ImageIcon[] frames;
    protected int currentFrame = 0;
    protected double currentTicks;
    protected final double maxTicks, ticksPerFrame;
    
    protected FramedAnimation(ImageIcon[] f, double delay, AnimationListener li){
        super(li);
        frames = f;
        maxTicks = delay;
        ticksPerFrame = Window.main.pacemaker.getDelay();
    }
    
    /**
     * Draws the current frame onto the given graphics.
     * @param g The graphics to draw on.
     * @param x The top left x.
     * @param y The top left y.
     */
    @Override
    public void animate(Graphics g, int x, int y){
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
                    listener.done(this);
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
    
    public abstract FramedAnimation mirror();
    
}
