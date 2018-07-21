
package level;

import items.Item;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class decides which rooms to generate.
 */
public class RoomDistribution implements Serializable{
    
    private final static long serialVersionUID = 690629590;
    
    private final Location location;
    private final int lower, upper;
    /**
     * This interface specifies the creation algorithm for a single Room given
     * the Location and depth.
     */
    public interface MakeRoom{
        Room make(Location loc, int depth);
    };
    /**
     * This interface specifies the creation algorithm for a single Room given
     * the Location, depth and a single Item.
     */
    public interface MakeItemRoom{
        Room make(Location loc, int depth, Item i);
    };
    
    /**
     * Creates a new instance.
     * @param loc The Location
     * @param l The minimum number of Rooms.
     * @param u The estimated maximum number of Rooms. (may be surpassed)
     */
    public RoomDistribution(Location loc, int l, int u){
        location = loc;
        lower = l;
        upper = u;
    }
    
    /**
     * Generates a List of Rooms.
     * @param forcedItems The list of Items that must be generated.
     * @param forcedRooms The list of room algorithms that must be executed.
     * @param depth The depth.
     * @return
     */
    public List<Room> generate(List<Item> forcedItems, List<MakeRoom> forcedRooms, int depth){
        int roomNum = Distribution.r.nextInt(upper-lower)+lower;
        List<Room> rooms = new LinkedList<>();
        List<Item> leftovers = new LinkedList<>();
        forcedRooms.stream().map((m) -> m.make(location, depth)).forEach((r) -> {
            forcedItems.removeAll(r.items());
            rooms.add(r);
            if(r.locked) leftovers.add(r.key);
        });
        forcedItems.addAll(leftovers);
        leftovers.clear();
        forcedItems.stream().map((i) -> populateForcedItems(i, depth, itemRoomAlgs, itemRoomDist)).forEach((r) -> {
            if(r.locked) leftovers.add(r.key);
            rooms.add(r);
        });
        int freeRooms = 0;
        for(roomNum -= rooms.size();roomNum>0;roomNum--){
            Room r = roomAlgs.get((int)roomDist.next()).make(location, depth);
            if(r.locked) leftovers.add(r.key);
            rooms.add(0, r);
            freeRooms++;
        }
        if(freeRooms!=0){
            for(Item i : leftovers)
                rooms.get(Distribution.r.nextInt(freeRooms)).randomlyPlop(i);
        }else if(!leftovers.isEmpty()){
            Room r;
            do{
                r = roomAlgs.get((int)roomDist.next()).make(location, depth);
            }while(r.locked);
            r.randomlyPlop(leftovers);
            rooms.add(r);
        }
        return rooms;
    }
    
    private Room populateForcedItems(Item f, int depth, List<MakeItemRoom> algorithms, Distribution dist){
        return algorithms.get((int)dist.next()).make(location, depth, f);
    }
    
    protected final static List<MakeRoom> roomAlgs = new LinkedList<>();
    protected final static List<MakeItemRoom> itemRoomAlgs = new LinkedList<>();
    protected final static Distribution roomDist = new Distribution(new int[]{10,6,6,2,1,3,3});
    protected final static Distribution itemRoomDist = new Distribution(new int[]{1,4,6});
    static{
        roomAlgs.add((loc, d) -> RoomBuilder.standard(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.standardLocked(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.stalagnate(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.garden(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.magicWellRoom(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.maze(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.storage(loc, d));
        
        itemRoomAlgs.add((loc, d, i) -> RoomBuilder.floodedVault(loc, i));
        itemRoomAlgs.add((loc, d, i) -> RoomBuilder.chasmVault(loc, i, d));
        itemRoomAlgs.add((loc, d, i) -> RoomBuilder.roomOfTraps(loc, i, d));
    }
    
}
