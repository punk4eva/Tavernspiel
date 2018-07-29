
package animation;

import gui.Window;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import listeners.AnimationListener;

/**
 *
 * @author Adam Whittaker
 */
public abstract class FramedAnimation extends TrackableAnimation{
    
    public transient ImageIcon[] frames;
    protected int currentFrame = 0;
    protected double currentTicks;
    protected final double maxTicks, ticksPerFrame;
    
    protected FramedAnimation(ImageIcon[] f, int delay, AnimationListener li){
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
    
}
