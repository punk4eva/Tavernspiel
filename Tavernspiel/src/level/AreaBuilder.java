
package level;

import exceptions.AreaCoordsOutOfBoundsException;
import gui.MainClass;
import items.Item;
import items.ItemBuilder;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import level.RoomDistribution.MakeRoom;
import logic.Distribution;
import logic.Utils.Unfinished;
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
     * @param roomDist The RoomDistribution.
     * @return The Area that was built.
     */
    @Unfinished("Generate locked rooms.")
    protected Area load(RoomDistribution roomDist){
        Area area = new Area(new Dimension(80, 80), location);
        int k = Distribution.r.nextInt(forcedKeys.size()+1);
        List<Item> normalItems = ItemBuilder.genRandom(Distribution.r.nextInt(24)+12), roomItems;
        
        List<Item> key1 = forcedKeys.subList(0, k), key2 = forcedKeys.subList(k, forcedKeys.size());
        for(int n=0, ch=Distribution.r.nextInt(7)+5;n<ch;n++){
            roomItems = partElements(normalItems, ch-n);
            if(!key1.isEmpty()) roomItems.add(key1.remove(0));
            else if(ch-n==1) roomItems.addAll(key1);
            selectAndBlit(area, roomDist.next(roomItems));
        }
        throw new UnsupportedOperationException("Not supported yet.");
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
        if(area.map[y-1][x].treadable) return 0;
        if(area.map[y][x+1].treadable) return 1;
        if(area.map[y+1][x].treadable) return 2;
        if(area.map[y][x-1].treadable) return 3;
        throw new IllegalStateException("No valid door direction.");
    }
    
    private <E> List<E> partElements(List<? extends E> ary, int remaining){
        List<E> ret = new LinkedList<>();
        Collections.shuffle(ary);
        if(remaining==1){
            ret.addAll(ary);
            return ret;
        }
        Iterator<? extends E> iter = ary.iterator();
        int n = 0, ch = Distribution.r.nextInt(Math.round(((float)ary.size())/remaining));
        while(iter.hasNext()&&n<ch){
            ret.add(iter.next());
            iter.remove();
            n++;
        }
        return ret;
    }
    
}
