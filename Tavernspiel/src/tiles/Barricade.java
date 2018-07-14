
package tiles;

import level.Location;
import logic.Distribution;

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
    
}
