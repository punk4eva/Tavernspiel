
package level;

import items.Item;
import java.util.ArrayList;
import level.RoomDistribution.MakeRoom;

/**
 *
 * @author Adam Whittaker
 */
public class AreaBuilder{
    
    public ArrayList<Item> forcedItems = new ArrayList<>();
    public ArrayList<MakeRoom> forcedRooms = new ArrayList<>();
    private final Location location;
    
    public AreaBuilder(Location loc){
        location = loc;
    }
    
    
    protected void flush(){
        forcedItems.clear();
        forcedRooms.clear();
    }

    protected Area load(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
