
package tiles;

import animation.Animation;
import animation.AnimationBuilder;
import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class AnimatedTile extends Tile{
    
    public Animation animation;
    
    public AnimatedTile(String tile, Animation an){
        super(tile, (ImageIcon) null);
        animation = an;
    }
    
    public AnimatedTile(String tile){
        super(tile, (ImageIcon) null);
        if(tile.startsWith("water")){
            animation = new Animation(ImageHandler.getWaterFrames(tile, 0));
        }else animation = AnimationBuilder.getAnimation(tile);
    }
    
    /**
     * Creates an Animated tile of water.
     * @param tile The pseudo-path to the water image.
     * @param x The offset.
     */
    public AnimatedTile(String tile, int x){
        super(tile, (ImageIcon) null);
        animation = new Animation(ImageHandler.getWaterFrames(tile, x));
    }
    
    /**
     * Creates an Animated tile of water.
     * @param loc The location.
     * @param x The offset.
     */
    public AnimatedTile(Location loc, int x){
        super("water", (ImageIcon) null);
        animation = new Animation(ImageHandler.getWaterFrames(loc, x));
    }
    
    public void addShaders(String shaderString, Location loc){
        animation.addShaders(shaderString, loc);
    }

}
