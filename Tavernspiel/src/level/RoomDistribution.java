
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
public class RoomDistribution{
    
    private final Location location;
    private final int lower, upper;
    /**
     * This interface specifies the creation algorithm for a single Room given
     * the Location and depth.
     */
    public interface MakeRoom extends Serializable{
        Room make(Location loc, int depth);
    };
    /**
     * This interface specifies the creation algorithm for a single Room given
     * the Location, depth and a single Item.
     */
    public interface MakeItemRoom extends Serializable{
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
     * @param feeling
     * @return
     */
    public List<Room> generate(List<Item> forcedItems, List<MakeRoom> forcedRooms, int depth, LevelFeeling feeling){
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
        forcedItems.stream().map((i) -> populateForcedItems(i, depth, itemRoomAlgs, feeling.itemRoomDist)).forEach((r) -> {
            if(r.locked) leftovers.add(r.key);
            rooms.add(r);
        });
        int freeRooms = 0;
        for(roomNum -= rooms.size();roomNum>0;roomNum--){
            Room r = roomAlgs.get((int)feeling.roomDist.next()).make(location, depth);
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
                r = roomAlgs.get((int)feeling.roomDist.next()).make(location, depth);
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
    static{
        roomAlgs.add((loc, d) -> RoomBuilder.standard(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.standardLocked(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.stalagnate(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.garden(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.magicWellRoom(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.maze(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.storage(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.exhibition(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.campfire(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.burntGarden(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.laboratory(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.library(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.kitchen(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.secretLibrary(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.lottery(loc, d));
        roomAlgs.add((loc, d) -> RoomBuilder.altar(loc, null, d));
        
        itemRoomAlgs.add((loc, d, i) -> RoomBuilder.floodedVault(loc, i, d));
        itemRoomAlgs.add((loc, d, i) -> RoomBuilder.chasmVault(loc, i, d));
        itemRoomAlgs.add((loc, d, i) -> RoomBuilder.roomOfTraps(loc, i, d));
        itemRoomAlgs.add((loc, d, i) -> RoomBuilder.altar(loc, i, d));
    }
    
}
