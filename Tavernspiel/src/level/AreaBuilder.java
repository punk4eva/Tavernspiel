
package level;

import exceptions.AreaCoordsOutOfBoundsException;
import gui.mainToolbox.Main;
import items.Item;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import level.RoomDistribution.MakeRoom;
import logic.Distribution;
import logic.Utils.Unfinished;
import pathfinding.CorridorBuilder;
import pathfinding.Graph;
import pathfinding.Point;

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
    @Unfinished("Generate forced rooms.")
    protected Area load(RoomDistribution roomDist, int depth){
        Area area = new Area(new Dimension(80, 80), location);
        List<Room> rooms = roomDist.generate(forcedItems, forcedRooms, depth);
        rooms.stream().forEach(r -> selectAndBlit(area, r));
        new CorridorBuilder(area).build();
        area.addDeco();
        area.graph = new Graph(area);
        
        return area;
    }
    
    private void selectAndBlit(Area area, Area add){
        int x=-1, y=-1, attempts = 0;
        while(attempts<=40){
            x = Distribution.r.nextInt(area.dimension.width-4-add.dimension.width) + 2;
            y = Distribution.r.nextInt(area.dimension.height-4-add.dimension.height) + 2;
            if(canBlit(area, add, x, y)) break;
            for(Point point : getMergeCoords(area, add, x, y)){
                int[] c = getConjoiningCoords(area, add, point, x, y);
                if(canBlit(area, add, c[0], c[1])){
                    try{
                        area.blitSafely(add, c[0], c[1]);
                    }catch(AreaCoordsOutOfBoundsException e){}
                    return;
                }
            }
            attempts++;
        }
        if(attempts>40) throw new IllegalStateException("Unable to blit the given area.");
        try{
            area.blit(add, x, y);
        }catch(AreaCoordsOutOfBoundsException e){
            e.printStackTrace(Main.exceptionStream);
        }
    }
    
    private List<Point> getMergeCoords(Area area, Area add, int nx, int ny){
        List<Point> points = new LinkedList<>();
        for(int y=ny-2, yCheck=ny+2+add.dimension.height;y<yCheck;y++){
            for(int x=nx-2, xCheck=nx+2+add.dimension.width;x<xCheck;x++){
                if(area.map[y][x]!=null&&area.map[y][x].equals("door")) points.add(new Point(x, y));
            }
        }
        return points;
    }
    
    private boolean canBlit(Area area, Area add, int nx, int ny){
        try{
            for(int y=ny-2, yCheck=ny+2+add.dimension.height;y<yCheck;y++){
                for(int x=nx-2, xCheck=nx+2+add.dimension.width;x<xCheck;x++){
                    if(area.map[y][x]!=null) return false;
                }
            }
        }catch(ArrayIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }
    
    private int[] getConjoiningCoords(Area area, Area add, Point point, int x, int y){
        int[] c = point.getCoords();
        switch(getDoorDirection(area, c[0], c[1])){
            case 0:
                c[0] -= Distribution.r.nextInt(add.dimension.width-2)+1;
                return c;
            case 1:
                c[0] -= add.dimension.width-1;
                c[1] += Distribution.r.nextInt(add.dimension.height-2)-1;
                return c;
            case 2:
                c[1] -= add.dimension.height-1;
                c[0] -= Distribution.r.nextInt(add.dimension.width-2)+1;
                return c;
            default:
                c[1] -= Distribution.r.nextInt(add.dimension.width-2)+1;
                return c;
        }
    }
    
    private int getDoorDirection(Area area, int x, int y){
        if(area.map[y-1][x]!=null&&area.map[y-1][x].treadable) return 0;
        if(area.map[y][x+1]!=null&&area.map[y][x+1].treadable) return 1;
        if(area.map[y+1][x]!=null&&area.map[y+1][x].treadable) return 2;
        if(area.map[y][x-1]!=null&&area.map[y][x-1].treadable) return 3;
        //@Unfinished remove debug
        System.err.println("Illegal Door Exception");
        area.debugMode = true;
        return 0;
        //throw new IllegalStateException("No valid door direction.");
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        location = Location.locationMap.get((String) in.readObject());
    }
    
    private void writeObject(ObjectOutputStream out) 
            throws IOException, ClassNotFoundException{
        out.defaultWriteObject();
        out.writeObject(location.name);
    }
    
}
