
package level;

import gui.mainToolbox.Main;
import items.Item;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import level.RoomDistribution.MakeRoom;
import logic.ConstantFields;
import pathfinding.Graph;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds Areas.
 */
public class AreaBuilder implements Serializable{
    
    private final static long serialVersionUID = 2141875790;
    
    public List<Item> forcedItems = new LinkedList<>(), forcedKeys = new LinkedList<>();
    public List<MakeRoom> forcedRooms = new LinkedList<>();
    private transient Location location;
    
    /**
     * Creates a new instance.
     * @param loc The location to use.
     */
    public AreaBuilder(Location loc){
        location = loc;
        forcedRooms.add((l, depth) -> RoomBuilder.depthExit(l, depth));
        forcedRooms.add((l, depth) -> RoomBuilder.depthEntrance(l, depth));
    }
    
    /**
     * Clears forced objects and adds depth entrances and exits for the next Area.
     */
    protected void reset(){
        forcedItems.clear();
        forcedRooms.clear();
        forcedRooms.add((loc, dep) -> RoomBuilder.depthExit(loc, dep));
        forcedRooms.add((loc, dep) -> RoomBuilder.depthEntrance(loc, dep));
    }

    /**
     * Builds the next Area.
     * @param roomDist The RoomDistribution.
     * @param depth The depth of the next Area.
     * @return The Area that was built.
     */
    protected Area load(RoomDistribution roomDist, int depth){
        location.feeling = LevelFeeling.getRandomFeeling();
        location.depth = depth;
        List<Room> rooms = roomDist.generate(forcedItems, forcedRooms, depth, location.feeling);
        RoomStructure area;
        if(location.feeling.structure==null) area = location.structure.apply(rooms);
        else area = location.feeling.structure.apply(rooms);
        area.generate();
        area.addDeco();
        area.graph = new Graph(area, null);
        Main.addMessage(ConstantFields.interestColor, location.feeling.description);
        
        return area;
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        location = Location.LOCATION_MAP.get((String) in.readObject());
    }
    
    private void writeObject(ObjectOutputStream out) 
            throws IOException, ClassNotFoundException{
        out.defaultWriteObject();
        out.writeObject(location.name);
    }
    
}
