
package containers;

import creatures.Creature;
import items.Item;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class Mimic extends Chest{
    
    private final static long serialVersionUID = 20321921;
    
    /**
     * Creates a new instance.
     * @param locName
     * @param item
     * @param x
     * @param y
     */
    public Mimic(String locName, Item item, int x, int y){
        super(locName, item, x, y);
    }
    
    @Override
    public void interact(Creature c, Area area){
        throw new UnsupportedOperationException("@Unfinished");
        //Spawn mimic creature.
        //Destroy mimic chest.
    }
    
}
