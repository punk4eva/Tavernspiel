
package level;

import items.Item;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import level.RoomDistribution.MakeRoom;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds Areas.
 */
public class AreaBuilder implements Serializable{
    
    public List<Item> forcedItems = new LinkedList<>();
    public List<MakeRoom> forcedRooms = new LinkedList<>();
    private final Location location;
    
    /**
     * Creates a new instance.
     * @param loc The location to use.
     */
    public AreaBuilder(Location loc){
        location = loc;
    }
    
    /**
     * Clears forced objects for the next Area.
     */
    protected void clear(){
        forcedItems.clear();
        forcedRooms.clear();
    }

    /**
     * Builds the next Area.
     * @return The Area that was built.
     */
    protected Area load(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
