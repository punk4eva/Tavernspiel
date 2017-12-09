
package pathfinding;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;
import level.Area;
import tiles.Door;

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
                if(area.map[y][x]==null||(!area.map[y][x].treadable&&!(area.map[y][x] instanceof Door))){
                    map[y][x] = new Point(x, y, null);
                }else{
                    map[y][x] = new Point(x, y);
                    if(area.map[y][x] instanceof Door){
                        wps.add(new Waypoint(x, y));
                    }
                }
            }
        }
        waypoints = wps.toArray(new Waypoint[wps.size()]);
        searcher = new Searcher(this, area);
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
    
    protected void initializeWaypoints(){
        for(int w=0;w<waypoints.length;w++){
            searcher.diagonalFloodfill(waypoints[w]);
            for(int ow=0;ow<waypoints.length;ow++) if(ow!=w)
                waypoints[w].pathsToWaypoints.put(waypoints[ow], followTrail(waypoints[ow].x, waypoints[ow].y));
        }
    }
    
    protected void use(){
        if(!used){
            used = true;
            return;
        }
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                if(map[y][x].checked!=null){
                    map[y][x] = new Point(x, y);
                }else{
                    map[y][x] = new Point(x, y, null);
                }
            }
        }
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
    
    public boolean tileFree(int x, int y){
        return map[y][x].checked!=null;
    }
    
    public void paint(Graphics g, int focusX, int focusY){
        for(int y=focusY;y<focusY+map.length*16;y+=16){
            for(int x=focusX;x<focusX+map[0].length*16;x+=16){ 
                Point point = map[(y-focusY)/16][(x-focusX)/16];
                if(point!=null) point.paint(g, x, y);
            }
        }
        for(Waypoint w : waypoints)
            for(Path p : w.pathsToWaypoints.values())
                p.paint(g, focusX, focusY);
    }

    public void moveOff(int x, int y){
        map[y][x].checked = false;
    }

    public void moveOn(int x, int y){
        map[y][x].checked = null;
    }
    
}
