
package pathfinding;

import creatureLogic.VisibilityOverlay;
import java.io.Serializable;
import level.Area;
import logic.Utils.Optimisable;
import logic.Utils.Unfinished;
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
    public final transient static Direction[] directions = Direction.values();
    public final transient static ExtendedDirection[] extendedDirections = ExtendedDirection.values();
    public FrontierAdd addCheck = (Serializable & FrontierAdd)(from, to) -> to.currentCost > from.currentCost + to.movementCost; //A Predicate to check whether to add a new Point to the frontier. Dijkstra's algorithm is default.
    
    public interface FrontierAdd extends Serializable{
        boolean check(Point from, Point to);
    }
    
    
    /**
     * Sets the Searcher into AStar mode.
     * @param end The destination to navigate to.
     */
    public void initializeEndpoint(Point end){
        addCheck = (from, to) -> to.currentCost > from.currentCost + to.movementCost;
        frontier = new PriorityQueue<>(p -> p.currentCost + p.getOODistance(end));
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
        graph.use();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : directions){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(area.map[ny][nx].treadable&&(!graph.map[ny][nx].checked||addCheck.check(p, graph.map[ny][nx]))){
                    graph.map[ny][nx].checked = true;
                    graph.map[ny][nx].cameFrom = p;
                    graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
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
        graph.use();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(ExtendedDirection dir : extendedDirections){
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
     * @deprecated Only use for short distance maneuvering.
     * @see findExpressRoute()
     */
    public Path findPath(Point start, Point end){
        graph.use();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(ExtendedDirection dir : extendedDirections){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(graph.map[ny][nx].checked!=null&&(!graph.map[ny][nx].checked||addCheck.check(p, graph.map[ny][nx]))){
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
        graph.use();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : directions){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(graph.map[ny][nx].equals(end)){
                    graph.map[ny][nx].cameFrom = p;
                    return graph.followTrail(end.x, end.y);
                }else if((graph.map[ny][nx].checked==null||graph.map[ny][nx].isCorridor)&&addCheck.check(p, graph.map[ny][nx])){
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
    @Unfinished("May be redundant")
    @Optimisable("Heuristic calculations only account for likely scenarios.")
    public Path findExpressRoute(Point start, Point end){
        Waypoint startStation = graph.getClosestWaypoint(start.x, start.y);
        Waypoint endStation = graph.getClosestWaypoint(end.x, end.y);
        initializeEndpoint(end);
        int commonDist = start.getOODistance(end);
        if(start.getOODistance(startStation)>commonDist||
                end.getOODistance(startStation)>commonDist||
                startStation.equals(endStation)){
            return findPath(start, end);
        }
        Path p = findPath(start, startStation);
        p.concatenate(startStation.pathsToWaypoints.get(endStation));
        p.concatenate(findPath(endStation, end));
        return p;
    }
    
    /**
     * Finds the shortest path between two points only taking into account tiles
     * that have been found by the visibility overlay.
     * @param start The starting point.
     * @param end The destination.
     * @param fov The Hero's field of view
     * @return The shortest path between start and end.
     */
    @Optimisable("Use Waypoint path concatenation.")
    public Path findPlayerRoute(Point start, Point end, VisibilityOverlay fov){
        graph.use();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(ExtendedDirection dir : extendedDirections){
                nx = p.x+dir.x;
                ny = p.y+dir.y;
                try{ if(fov.map[ny][nx]!=0&&graph.map[ny][nx].checked!=null&&(!graph.map[ny][nx].checked||addCheck.check(p, graph.map[ny][nx]))){
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
    
}
