
package animation;

import gui.Window;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 * 
 * Represents the Animation for water.
 */
public class WaterAnimation implements Animation{
    
    public transient ImageIcon[] frames;
    protected int currentFrame = 0;
    protected double currentTicks;
    protected final double maxTicks, ticksPerFrame;
    private final String locName;
    private String shaderString;
    public int x;
    
    public WaterAnimation(String shader, Location loc, int _x){
        frames = ImageHandler.getWaterFrames(loc, _x);
        x = _x;
        maxTicks = 110;
        ticksPerFrame = Window.main.pacemaker.getDelay();
        addShaders(shader, loc);
        locName = loc.name;
    }
    
    public WaterAnimation(Location loc, int x){
        frames = ImageHandler.getWaterFrames(loc, x);
        maxTicks = 110;
        ticksPerFrame = Window.main.pacemaker.getDelay();
        locName = loc.name;
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
        shaderString = shader;
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
            if(currentFrame>=frames.length) currentFrame = 0;
        }
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        Location loc = Location.locationMap.get(locName);
        frames = ImageHandler.getWaterFrames(loc, x);
        if(shaderString!=null) addShaders(shaderString, loc);
    }
    
}
