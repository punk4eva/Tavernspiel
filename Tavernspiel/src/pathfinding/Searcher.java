
package pathfinding;

import logic.Utils.Optimisable;
import pathfinding.Point.Direction;
import pathfinding.PriorityQueue.Compare;

/**
 *
 * @author Adam Whittaker
 */
public class Searcher{
    
    public PriorityQueue<Point> frontier = new PriorityQueue<>(p -> p.currentCost);
    public final Graph graph;
    public static Direction[] directions = Direction.values();
    public FrontierAdd addCheck = (from, to) -> to.currentCost > from.currentCost + to.movementCost; //A Predicate to check whether to add a new Point to the frontier. Dijkstra's algorithm is default.
    interface FrontierAdd{
        boolean check(Point from, Point to);
    }
    
    
    /**
     * Sets the Searcher into BreadthFirst mode.
     */
    public void breadthFirst(){
        addCheck = (from, to) -> false; 
        frontier = new PriorityQueue<>(p -> 0);
    }
    /**
     * Sets the Searcher into Dijkstra mode.
     */
    public void dijkstra(){
        addCheck = (from, to) -> to.currentCost > from.currentCost + to.movementCost; 
        frontier = new PriorityQueue<>(p -> p.currentCost);
    }
    /**
     * Sets the Searcher into GreedyBest mode.
     * @param end The destination to navigate to.
     */
    public void greedyBest(Point end){
        addCheck = (from, to) -> true;
        frontier = new PriorityQueue<>(p -> p.getOODistance(end));
    }
    /**
     * Sets the Searcher into AStar mode.
     * @param end The destination to navigate to.
     */
    public void AStar(Point end){
        addCheck = (from, to) -> to.currentCost > from.currentCost + to.movementCost;
        frontier = new PriorityQueue<>(p -> p.currentCost + p.getOODistance(end));
    }
    /**
     * Sets the Searcher into AStar mode with custom weights on the greedyBest
     * and Dijkstra part of the algorithm. It is recommended to set the greedyBest
     * to 1.0 and the Dijkstra to the lower quartile of the list of movement costs.
     * @param end The destination to navigate to.
     * @param weights The weights to place on [0] the Dijkstra and [1] the
     * greedyBest algorithms.
     */
    public void AStar(Point end, double... weights){
        addCheck = (from, to) -> to.currentCost > from.currentCost + to.movementCost;
        frontier = new PriorityQueue<>(p -> (long)(((double)p.currentCost)*weights[0] + ((double)p.getOODistance(end))*weights[1]));
    }
    /**
     * Sets the Searcher into Custom mode. It will run on a custom pathfinding
     * algorithm based on the inputs.
     * @param add The Predicate that controls whether to add a point to the frontier
     * aside from the fact that it is unexplored.
     * @param comp The priority function for exploring new points.
     */
    public void custom(FrontierAdd add, Compare comp){
        addCheck = add;
        frontier = new PriorityQueue<>(comp);
    }
    
    
    public Searcher(Graph g){
        graph = g;
    }
    
    /**
     * Flood fills the Searcher's graph using a breadth first search prioritizing
     * based on the Searcher's comparator in PriorityQueue.
     * @param start The starting point.
     */
    public void floodfill(Point start){
        graph.use();
        frontier.clear();
        start.currentCost = 0;
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : directions){
                nx = dir.x.update(p.x);
                ny = dir.y.update(p.y);
                try{ if(graph.map[ny][nx].checked!=null&&(!graph.map[ny][nx].checked||addCheck.check(p, graph.map[ny][nx]))){
                    graph.map[ny][nx].checked = true;
                    graph.map[ny][nx].cameFrom = p;
                    graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
                    frontier.add(graph.map[ny][nx]);
                }}catch(ArrayIndexOutOfBoundsException e){}
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
        graph.resetMap();
        frontier.clear();
        frontier.add(start);
        int nx, ny;
        while(!frontier.isEmpty()){
            Point p = frontier.poll();
            for(Direction dir : directions){
                nx = dir.x.update(p.x);
                ny = dir.y.update(p.y);
                if(!graph.map[ny][nx].checked||addCheck.check(p, graph.map[ny][nx])){
                    graph.map[ny][nx].checked = true;
                    graph.map[ny][nx].cameFrom = p;
                    graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
                    if(graph.map[ny][nx].equals(end)) break;
                    frontier.add(graph.map[ny][nx]);
                }
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
    @Optimisable("Heuristic calculations only account for likely scenarios.")
    public Path findExpressRoute(Point start, Point end){
        Waypoint startStation = graph.getClosestWaypoint(start.x, start.y);
        Waypoint endStation = graph.getClosestWaypoint(end.x, end.y);
        AStar(end);
        int commonDist = start.getOODistance(end);
        if(start.getOODistance(startStation)>commonDist||
                end.getOODistance(startStation)>commonDist||
                startStation.equals(endStation)){
            return findPath(start, end);
        }
        return findPath(start, startStation).concatenate(startStation.pathsToWaypoints.get(endStation)).concatenate(findPath(endStation, end));
    }  
    
}
