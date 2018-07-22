
package tiles;

import animation.Animation;
import animation.WaterAnimation;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import level.Location;

/**
 *
 * @author Adam Whittaker
 */
public class AnimatedTile extends Tile{
    
    public Animation animation;
    
    /**
     * Creates an instance.
     * @param tile The name of the Tile.
     * @param an The Animation.
     */
    public AnimatedTile(String tile, Animation an){
        super(tile, (ImageIcon) null);
        animation = an;
    }
    
    /**
     * Creates an instance.
     * @param tile The name of the Tile.
     * @param an The Animation.
     * @param t The treadability.
     * @param tr The transparency.
     * @param f The flammability.
     */
    public AnimatedTile(String tile, Animation an, boolean t, boolean f, boolean tr){
        super(tile, (ImageIcon) null, t, f, tr);
        animation = an;
    }
    
    /**
     * Creates an Animated tile of water.
     * @param loc The location.
     * @param x The offset.
     */
    public AnimatedTile(Location loc, int x){
        super("water", (ImageIcon) null);
        animation = new WaterAnimation(loc, x);
    }
    
    /**
     * Adds a shader to this Tile.
     * @param shaderString The string tag of the shader.
     * @param loc The Location.
     */
    public void addShaders(String shaderString, Location loc){
        ((WaterAnimation)animation).addShaders(shaderString, loc);
    }
    
    @Override
    public void paint(Graphics g, int x, int y){
        animation.animate(g, x, y);
    }

}
