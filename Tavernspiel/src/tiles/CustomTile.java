
package tiles;

import java.util.function.Function;
import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class CustomTile extends Tile{
    
    protected Function<Location, Tile> loader;
    
    public CustomTile(Location loc, Function<Location, Tile> l){
        super(null, (ImageIcon)null, false, false, false);
        loader = l;
        Tile t = loader.apply(loc);
        name = t.name;
        image = t.image;
        transparent = t.transparent;
        flammable = t.flammable;
        treadable = t.treadable;
    }
    
    /**
     * Adds shaders (overlay) to the given ImageIcon.
     * @param i The ImageIcon.
     * @param shader The regular expression type string indicating where
     * shaders come from or if it is a special shader.
     * @param loc The location whose tileset to use.
     * @return The shaded ImageIcon.
     */
    public static final ImageIcon addShaders(ImageIcon i, String shader, Location loc){
        if(shader.equals("well") || shader.equals("alchemypot"))
            return ImageHandler.combineIcons(i, loc.getImage(shader));
        return ImageHandler.combineIcons(i, loc.getImage("shader" + shader));
    }
    
}
