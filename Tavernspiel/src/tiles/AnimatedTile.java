
package tiles;

import animation.Animation;
import animation.assets.WaterAnimation;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import level.Location;

/**
 *
 * @author Adam Whittaker
 * 
 * A Tile that needs to be Animated.
 */
public class AnimatedTile extends Tile{
    
    public transient Animation animation;
    
    /**
     * Creates an instance.
     * @param loc The Location.
     * @param tile The name of the Tile.
     * @param an The Animation.
     * @param t The treadability.
     * @param tr The transparency.
     * @param f The flammability.
     */
    public AnimatedTile(Location loc, String tile, Animation an, boolean t, boolean f, boolean tr){
        super(tile, (ImageIcon) null, t, f, tr);
        animation = an;
        description = TileDescriptionBuilder.getDescription(name, loc);
    }
    
    /**
     * Creates an Animated tile of water.
     * @param loc The location.
     * @param x The offset.
     */
    public AnimatedTile(Location loc, int x){
        super("water", (ImageIcon) null, true, false, true);
        animation = new WaterAnimation(loc, x);
        description = TileDescriptionBuilder.getDescription(name, loc);
    }
    
    @Override
    public void paint(Graphics2D g, int x, int y){
        animation.animate(g, x, y);
    }

}
