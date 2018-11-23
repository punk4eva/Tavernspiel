
package animation.assets;

import animation.Animation;
import animation.TickedAnimation;
import gui.Window;
import gui.mainToolbox.Pacemaker;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 * 
 * Represents the Animation for water.
 */
public class WaterAnimation implements Animation, TickedAnimation{
    
    private final static long serialVersionUID = 174801232;
    
    public transient ImageIcon[] frames;
    protected int currentFrame = 0;
    protected double currentTicks, ticksPerFrame;
    protected final double maxTicks;
    
    public WaterAnimation(String shader, Location loc, int _x){
        frames = ImageHandler.getWaterFrames(loc, _x);
        maxTicks = 110;
        try{
            ticksPerFrame = Window.main.pacemaker.getDelay();
        }catch(NullPointerException e){
            Pacemaker.registerWaitingAnimation(this);
        }
        addShaders(shader, loc);
    }
    
    public WaterAnimation(Location loc, int x){
        frames = ImageHandler.getWaterFrames(loc, x);
        maxTicks = 110;
        try{
            ticksPerFrame = Window.main.pacemaker.getDelay();
        }catch(NullPointerException e){
            Pacemaker.registerWaitingAnimation(this);
        }
    }
    
    /**
     * Adds shaders (overlay) to this animation.
     * @param shader The regular expression type string indicating where
     * shaders come from or if it is a special shader.
     * @param loc The location whose tileset to use.
     */
    public final void addShaders(String shader, Location loc){
        if(shader.equals("well") || shader.equals("alchemypot")){
            ImageIcon shaderIcon = loc.getImage(shader);
            for(int n=0;n<frames.length;n++){
                frames[n] = ImageHandler.combineIcons(frames[n], shaderIcon);
            }
        }else{
            ImageIcon shaderIcon = loc.getImage("shader" + shader);
            for(int n=0;n<frames.length;n++){
                frames[n] = ImageHandler.combineIcons(frames[n], shaderIcon);
            }
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
    
    @Override
    public void setTicksPerFrame(double tpf){
        ticksPerFrame = tpf;
    }

    /**
     * Recalculates the frame number.
     */
    protected void recalc(){
        currentTicks += ticksPerFrame;
        while(currentTicks>maxTicks){
            currentTicks -= maxTicks;
            currentFrame++;
            if(currentFrame>=frames.length) currentFrame = 0;
        }
    }
    
}
