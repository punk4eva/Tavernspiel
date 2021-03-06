
package pathfinding;

import creatureLogic.VisibilityOverlay;
import java.io.Serializable;
import level.Area;
import pathfinding.Point.Direction;
import pathfinding.Point.ExtendedDirection;
import pathfinding.PriorityQueue.Compare;

/**
 *
 * @author Adam Whittaker
 * 
 * This class stores pathfinding algorithms and implements them on a Graph.
 */
public class Searcher implements Serializable{
    
    private final static long serialVersionUID = 690625658;
    
    private final Area area;
    public PriorityQueue<Point> frontier = new PriorityQueue<>((Serializable & Compare<Point>)p -> p.currentCost);
    public final Graph graph;
    public final transient static Direction[] DIRECTIONS = Direction.values();
    public final transient static ExtendedDirection[] EXTENDED_DIRECTIONS = ExtendedDirection.values();
    public FrontierAdd addCheck = (Serializable & FrontierAdd)(from, to) -> to.currentCost > from.currentCost + to.movementCost; //A Predicate to check whether to add a new Point to the frontier. Dijkstra's algorithm is default.
    
    public interface FrontierAdd extends Serializable{
        boolean check(Point from, Point to);
    }
    
    
    /**
     * Sets the Searcher into AStar mode.
     * @param end The destination to navigate to.
     */
    public void initializeEndpoint(Point end){
        frontier = new PriorityQueue<>(p -> p.currentCost + p.getOMDistance(end));
    }
    
    /**
     * Creates a new instance.
     * @param g The Graph
     * @param a The Area
     */
    public Searcher(Graph g, Area a){
        graph = g;
        area = a;
    }
    
    /**
     * Flood fills the Searcher's graph using a breadth first search prioritizing
     * based on the Searcher's comparator in PriorityQueue. Checks stuff.
     * @param start The starting point.
     */
    public void checkedFloodfill(Point start){
        graph.reset();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : DIRECTIONS){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if((area.map[ny][nx].treadable||graph.map[ny][nx] instanceof Waypoint)&&(!graph.map[ny][nx].checked||addCheck.check(p, graph.map[ny][nx]))){
                    graph.map[ny][nx].checked = true;
                    graph.map[ny][nx].cameFrom = p;
                    graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
                    frontier.add(graph.map[ny][nx]);
                }}catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }
    
    /**
     * Flood fills the Searcher's graph using a breadth first search and avoids
     * corridors.
     * @param start The starting point.
     */
    public void structuredFloodfill(Point start){
        graph.reset();
        frontier.clear();
        start.isCorridor = true;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : DIRECTIONS){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(graph.map[ny][nx].cameFrom==null&&!graph.map[ny][nx].isCorridor){
                    graph.map[ny][nx].cameFrom = p;
                    frontier.add(graph.map[ny][nx]);
                }}catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }
    
    /**
     * Flood fills the Searcher's graph using a breadth first search prioritizing
     * based on the Searcher's comparator in PriorityQueue.
     * @param start The starting point.
     */
    public void extendedFloodfill(Point start){
        graph.reset();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(ExtendedDirection dir : EXTENDED_DIRECTIONS){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(area.map[ny][nx].treadable&&(!graph.map[ny][nx].checked/*||addCheck.check(p, graph.map[ny][nx])*/)){
                    graph.map[ny][nx].checked = true;
                    graph.map[ny][nx].cameFrom = p;
                    graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
                    frontier.add(graph.map[ny][nx]);
                }}catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }
    
    /**
     * Finds and returns the shortest path between two points.
     * @param start The starting point.
     * @param end The destination.
     * @return The shortest path between start and end.
     * @see findExpressRoute()
     */
    private Path findPath(Point start, Point end){
        graph.reset();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(ExtendedDirection dir : EXTENDED_DIRECTIONS){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(graph.map[ny][nx].checked!=null&&(!graph.map[ny][nx].checked/*||addCheck.check(p, graph.map[ny][nx])*/)){
                    graph.map[ny][nx].checked = true;
                    graph.map[ny][nx].cameFrom = p;
                    graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
                    if(graph.map[ny][nx].equals(end)) break;
                    frontier.add(graph.map[ny][nx]);
                }}catch(ArrayIndexOutOfBoundsException e){}
            }
        }
        return graph.followTrail(end.x, end.y);
    }
    
    /**
     * Finds the shortest path between two points only taking into account tiles
     * that have been found by the visibility overlay.
     * @param start The starting point.
     * @param end The destination.
     * @param fov The Hero's field of view
     * @return The shortest path between start and end.
     */
    private Path findPlayerPath(Point start, Point end, VisibilityOverlay fov){
        graph.reset();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(ExtendedDirection dir : EXTENDED_DIRECTIONS){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(fov.map[ny][nx]!=0&&graph.map[ny][nx].checked!=null&&(!graph.map[ny][nx].checked/*||addCheck.check(p, graph.map[ny][nx])*/)){
                    graph.map[ny][nx].checked = true;
                    graph.map[ny][nx].cameFrom = p;
                    graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
                    if(graph.map[ny][nx].equals(end)) break;
                    frontier.add(graph.map[ny][nx]);
                }}catch(ArrayIndexOutOfBoundsException e){}
            }
        }
        return graph.followTrail(end.x, end.y);
    }
    
    /**
     * Finds the shortest path between two points going through unused space.
     * @param start
     * @param end
     * @return
     */
    public Path findNullPath(Point start, Point end){
        graph.reset();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : DIRECTIONS){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(graph.map[ny][nx].equals(end)){
                    graph.map[ny][nx].cameFrom = p;
                    return graph.followTrail(end.x, end.y);
                }else if((graph.map[ny][nx].checked==null||graph.map[ny][nx].isCorridor)/*&&addCheck.check(p, graph.map[ny][nx])*/){
                    graph.map[ny][nx].checked = true;
                    graph.map[ny][nx].cameFrom = p;
                    graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
                    if(graph.map[ny][nx].equals(end)) break;
                    frontier.add(graph.map[ny][nx]);
                }}catch(ArrayIndexOutOfBoundsException e){}
            }
        }
        return graph.followTrail(end.x, end.y);
    }
    
    /**
     * Finds the shortest path between two points using predefined sub-paths to
     * speed up calculation.
     * @param start The starting point.
     * @param end The destination.
     * @return The shortest path between start and end.
     */
    public Path findExpressRoute(Point start, Point end){
        if(graph.navMesh == null || start.roomNum == end.roomNum){
            initializeEndpoint(end);
            return findPath(start, end);
        }else{
            Path b = graph.navMesh.retrievePath(start.roomNum, end.roomNum);
            initializeEndpoint(b.get(0));
            Path a = findPath(start, b.get(0));
            initializeEndpoint(end);
            a.concatenate(b);
            start = a.remove(a.size()-1);
            a.concatenate(findPath(start, end));
            return a;
        }
    }
    
    /**
     * Finds the shortest path between two points only taking into account tiles
     * that have been found by the visibility overlay.
     * @param start The starting point.
     * @param end The destination.
     * @param fov The Hero's field of view
     * @return The shortest path between start and end.
     */
    public Path findPlayerRoute(Point start, Point end, VisibilityOverlay fov){
        if(graph.navMesh == null || start.roomNum == end.roomNum){
            initializeEndpoint(end);
            return findPlayerPath(start, end, fov);
        }else{
            Path b = graph.navMesh.retrievePath(start.roomNum, end.roomNum);
            initializeEndpoint(b.get(0));
            Path a = findPlayerPath(start, b.get(0), fov);
            initializeEndpoint(end);
            a.concatenate(b);
            start = a.remove(a.size()-1);
            a.concatenate(findPlayerPath(start, end, fov));
            return a;
        }
    }
    
}
