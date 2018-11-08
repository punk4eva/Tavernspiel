
package pathfinding;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;
import level.Area;
import tiles.assets.Barricade;
import tiles.assets.Door;

/**
 *
 * @author Adam Whittaker
 * 
 * This class proxies and extends functionality of an Area when pathfinding.
 */
public class Graph implements Serializable{
    
    private final static long serialVersionUID = 69062958;
    
    public Point[][] map;
    public Waypoint[] waypoints;
    public final Searcher searcher;
    public NavigationMesh navMesh;
    
    /**
     * Creates an instance.
     * @param area
     * @param corridors The location of corridors.
     */
    public Graph(Area area, boolean[][] corridors){
        map = new Point[area.dimension.height][area.dimension.width];
        LinkedList<Waypoint> wps = new LinkedList<>();
        for(int y=0;y<area.dimension.height;y++){
            for(int x=0;x<area.dimension.width;x++){
                if(area.map[y][x]==null||(!area.map[y][x].treadable&&!(area.map[y][x] instanceof Door || area.map[y][x] instanceof Barricade))){
                    map[y][x] = new Point(x, y, null);
                }else{
                    if(area.map[y][x] instanceof Door || area.map[y][x] instanceof Barricade){
                        map[y][x] = new Waypoint(x, y);
                        wps.add((Waypoint)map[y][x]);
                    }else map[y][x] = new Point(x, y);
                }
                if(corridors!=null&&corridors[y][x]) map[y][x].isCorridor = true;
            }
        }
        waypoints = wps.toArray(new Waypoint[wps.size()]);
        searcher = new Searcher(this, area);
        if(corridors!=null && area.dimension.height>100 && area.dimension.width > 100) navMesh = new NavigationMesh(this, area);
    }
    
    /**
     * Returns the closest WayPoint to the given coordinates.
     * @param x
     * @param y
     * @return
     */
    public Waypoint getClosestWaypoint(int x, int y){
        Waypoint ret = waypoints[0];
        for(int n=1;n<waypoints.length;n++){
            if(ret.getOMDistance(x, y) > 
                    waypoints[n].getOMDistance(x, y)){
                ret = waypoints[0];
            }
        }
        return ret;
    }
    
    /**
     * Follows the trail of Points in the graph ending with the given
     * coordinates to build a Path after a pathfinding algorithm was run.
     * @param x
     * @param y
     * @return
     */
    public Path followTrail(int x, int y){
        Point p = map[y][x];
        LinkedList<Point> ret = new LinkedList<>();
        while(p.cameFrom!=null){
            ret.add(p);
            p = p.cameFrom;
        }
        ret.add(map[p.y][p.x]);
        return new Path(ret.descendingIterator());
    }
    
    /**
     * Ensures this Graph is ready for use by reseting all trails.
     */
    public void reset(){
        for(Point[] row : map)
            for(int x = 0; x<map[0].length; x++) row[x].reset();
    }
    
    /**
     * Checks if the given Tile is free.
     * @param x 
     * @param y
     * @return
     */
    public boolean tileFree(int x, int y){
        try{
            return map[y][x].checked!=null;
        }catch(ArrayIndexOutOfBoundsException e){return false;}
    }
    
    /**
     * Paints a debug overlay of this Graph.
     * @param g The Graphics
     * @param focusX
     * @param focusY
     * @param area
     */
    public void debugPaint(Graphics g, int focusX, int focusY, Area area){
        /*for(int y=focusY;y<focusY+map.length*16;y+=16){
            for(int x=focusX;x<focusX+map[0].length*16;x+=16){ 
                Point point = map[(y-focusY)/16][(x-focusX)/16];
                if(point!=null) point.paint(g, x, y, area);
            }
        }
        if(navMesh!=null) navMesh.debugPaint(g, focusX, focusY);*/
    }

    /**
     * Handles a Creature moving off of the given coordinates.
     * @param x
     * @param y
     */
    public void moveOff(int x, int y){
        map[y][x].checked = false;
    }

    /**
     * Handles a Creature moving onto the given coordinates.
     * @param x
     * @param y
     */
    public void moveOn(int x, int y){
        map[y][x].checked = null;
    }
    
}
