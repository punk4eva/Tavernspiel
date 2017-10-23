
package level;

import exceptions.AreaCoordsOutOfBoundsException;
import gui.MainClass;
import items.Item;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import level.RoomDistribution.MakeRoom;
import logic.Distribution;
import logic.Utils.Unfinished;
import pathfinding.CorridorBuilder;
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
    private final Location location;
    
    /**
     * Creates a new instance.
     * @param loc The location to use.
     */
    public AreaBuilder(Location loc){
        location = loc;
        forcedRooms.add((l) -> RoomBuilder.depthExit(l));
        forcedRooms.add((l) -> RoomBuilder.depthEntrance(l));
    }
    
    /**
     * Clears forced objects and adds depth entrances and exits for the next Area.
     */
    protected void reset(){
        forcedItems.clear();
        forcedRooms.clear();
        forcedRooms.add((loc) -> RoomBuilder.depthExit(loc));
        forcedRooms.add((loc) -> RoomBuilder.depthEntrance(loc));
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
        
        List<Room> rooms = new LinkedList<>(), lockedRooms = new LinkedList<>();
        //Adds the unlocked natural rooms.
        for(int n=0, roomNum = Distribution.r.nextInt(7)+3;n<roomNum;n++){
            rooms.add(roomDist.next());
        }
        //Adds the locked Rooms to a separate list and generates their keys.
        List<Item> keys = new LinkedList<>();
        for(int n=0, roomNum = Distribution.r.nextInt(3)+1;n<roomNum;n++){
            Room room = roomDist.nextLocked(depth);
            lockedRooms.add(room);
            keys.add(room.key);
        }
        //Adds forcedKeys to forcedItems.
        forcedItems.addAll(forcedKeys);
        rooms.get(Distribution.r.nextInt(rooms.size())).randomlyPlop(keys.remove(0));
        //Does something
        for(int n=0;n<lockedRooms.size()-1;n++){
            Room room = lockedRooms.get(n);
            if(room.itemMap==null){
                forcedItems.add(room.getReceptacle().swapItem(keys.remove(0)));
            }else{
                room.randomlyPlop(keys.remove(0));
            }
        }
        //Adds the forced Items and keys to the normally generating rooms.
        while(!forcedItems.isEmpty()){
            rooms.get(Distribution.r.nextInt(rooms.size())).randomlyPlop(forcedItems.remove(0));
        }
        //Adds the forced Rooms.
        forcedRooms.stream().forEach((mr) -> {
            rooms.add(mr.make(location));
        });
        //Blits the Rooms to the Area.
        rooms.addAll(lockedRooms);
        rooms.stream().forEach(r -> selectAndBlit(area, r));
        
        new CorridorBuilder(area).build();
        return area;
        //throw new UnsupportedOperationException("Not supported yet.");
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
            e.printStackTrace(MainClass.exceptionStream);
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
        throw new IllegalStateException("No valid door direction.");
    }
    
}
