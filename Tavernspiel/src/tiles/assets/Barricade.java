
package tiles.assets;

import level.Location;
import logic.Distribution;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a burnable tile as an entrance rather than a decoration.
 */
public class Barricade extends Tile{
    
    private static final Distribution bookshelfChance = new Distribution(1,3);
    
    
    
    /**
     * Creates a new instance.
     * @param loc The Location
     */
    public Barricade(Location loc){
        super(bookshelfChance.chance() ? "bookshelf" : "barricade",
                loc, false, true, false);
    }
    
    /**
     * Creates a new instance.
     * @param str The name of this Barricade.
     * @param loc The Location
     */
    public Barricade(String str, Location loc){
        super(str, loc, false, true, false);
    }
    
}
