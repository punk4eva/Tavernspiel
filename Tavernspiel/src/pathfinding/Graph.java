
package pathfinding;

import java.io.Serializable;
import java.util.ArrayList;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class Graph implements Serializable{
    
    Point[][] map;
    Waypoint[] waypoints;
    
    public Graph(Area area){
        map = new Point[area.dimension.height][area.dimension.width];
        ArrayList<Waypoint> wps = new ArrayList<>();
        for(int y=0;y<area.dimension.height;y++){
            for(int x=0;x<area.dimension.width;x++){
                if(area.map[y][x].treadable){
                    map[y][x] = new Point(x, y);
                    if(area.map[y][x].equals("Door")){
                        wps.add(new Waypoint(x, y));
                    }
                }else{
                    map[y][x] = new Point(x, y, null);
                }
            }
        }
        waypoints = wps.toArray(new Waypoint[wps.size()]);
        initializeWaypoints();
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
        Point ref = new Point(x, y);
        ArrayList<Point> ret = new ArrayList<>();
        while(!map[y][x].cameFrom.equals(ref)){
            ret.add(map[y][x]);
            x = map[y][x].cameFrom.x;
            y = map[y][x].cameFrom.y;
        }
        ret.add(map[y][x]);
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
    
    protected void resetMap(){
        Point[][] ret = new Point[map.length][map[0].length];
        for(int y=0;y<ret.length;y++){
            for(int x=0;x<ret[0].length;x++){
                if(map[y][x].checked!=null){
                    map[y][x] = new Point(x, y);
                }else{
                    map[y][x] = new Point(x, y, null);
                }
            }
        }
    }
    
}
