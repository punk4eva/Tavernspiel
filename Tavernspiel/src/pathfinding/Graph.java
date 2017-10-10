
package pathfinding;

import java.io.Serializable;
import java.util.LinkedList;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class Graph implements Serializable{
    
    private final static long serialVersionUID = 69062958;
    
    Point[][] map;
    Waypoint[] waypoints;
    private boolean used = false;
    public final Searcher searcher;
    
    public Graph(Area area){
        map = new Point[area.dimension.height][area.dimension.width];
        LinkedList<Waypoint> wps = new LinkedList<>();
        for(int y=0;y<area.dimension.height;y++){
            for(int x=0;x<area.dimension.width;x++){
                if(area.map[y][x]==null||!area.map[y][x].treadable) map[y][x] = new Point(x, y, null);
                else{
                    map[y][x] = new Point(x, y);
                    if(area.map[y][x].equals("Door")){
                        wps.add(new Waypoint(x, y));
                    }
                }
            }
        }
        waypoints = wps.toArray(new Waypoint[wps.size()]);
        //initializeWaypoints();
        searcher = new Searcher(this);
    }
    
    public Waypoint getClosestWaypoint(int x, int y){
        Waypoint ret = waypoints[0];
        for(int n=1;n<waypoints.length;n++){
            if(ret.getOODistance(x, y) > 
                    waypoints[n].getOODistance(x, y)){
                ret = waypoints[0];
            }
        }
        return ret;
    }
    
    public Waypoint getNthClosestWaypoint(final int x, final int y, final int n){
        return (Waypoint) new PriorityQueue(waypoints, (p) -> ((Waypoint) p).getOODistance(x, y)).get(n);
    }
    
    public Path followTrail(int x, int y){
        Point p = map[y][x];
        LinkedList<Point> ret = new LinkedList<>();
        while(p.cameFrom!=null){
            ret.add(p);
            p = p.cameFrom;
        }
        ret.add(map[p.y][p.x]);
        return new Path(ret.toArray(new Point[ret.size()])).reverse();
    }
    
    private void initializeWaypoints(){
        Searcher searcher = new Searcher(this);
        for(int w=0;w<waypoints.length;w++){
            searcher.floodfill(waypoints[w]);
            for(int ow=0;ow<waypoints.length;ow++){
                if(ow!=w){
                    waypoints[w].pathsToWaypoints.put(waypoints[ow], followTrail(waypoints[ow].x, waypoints[ow].y));
                }
            }
        }
    }
    
    protected void use(){
        if(!used) return;
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                if(map[y][x].checked!=null){
                    map[y][x] = new Point(x, y);
                }else{
                    map[y][x] = new Point(x, y, null);
                }
            }
        }
        used = true;
    }
    
    protected void resetMap(){
        if(!used) return;
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                if(map[y][x].checked!=null){
                    map[y][x] = new Point(x, y);
                }else{
                    map[y][x] = new Point(x, y, null);
                }
            }
        }
        used = false;
    }
    
}
