
package tiles;

import animation.Animation;
import java.awt.Graphics;
import java.awt.Image;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class AnimatedTile extends Tile{
    
    public Animation animation;
    
    public AnimatedTile(String tile, Animation an){
        super(tile, (Image) null);
        animation = an;
    }
    
    public AnimatedTile(String tile){
        super(tile, (Image) null);
        animation = new Animation(ImageHandler.getFrames(tile, 0));
    }
    
    public AnimatedTile(String tile, int x){
        super(tile, (Image) null);
        animation = new Animation(ImageHandler.getFrames(tile, x));
    }
    
    public void addShaders(String shaderString, Location loc){
        animation.addShaders(shaderString, loc);
    }

}
