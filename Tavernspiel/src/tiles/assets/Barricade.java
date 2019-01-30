
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
    public static final String BOOKSHELF_DESC = "This shelf is full of unorganized books.", 
            BARRICADE_DESC = "This is an old, dusty barricade of dry wood.";
    
    
    
    /**
     * Creates a new instance.
     * @param loc The Location
     */
    public Barricade(Location loc){
        super(bookshelfChance.chance() ? "bookshelf" : "barricade", "ERROR",
                loc, false, true, false);
        if(name.contains("bookshelf")) description.layers[0] = BOOKSHELF_DESC;
        else description.layers[0] = BARRICADE_DESC;
    }
    
    /**
     * Creates a new instance.
     * @param str The name of this Barricade.
     * @param loc The Location
     */
    public Barricade(String str, Location loc){
        super(str, "ERROR", loc, false, true, false);
        if(name.contains("bookshelf")) description.layers[0] = BOOKSHELF_DESC;
        else description.layers[0] = BARRICADE_DESC;
    }
    
}
