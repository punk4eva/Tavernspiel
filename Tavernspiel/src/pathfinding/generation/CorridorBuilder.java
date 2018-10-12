
package pathfinding.generation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import level.Area;
import logic.Distribution;
import pathfinding.Graph;
import pathfinding.Path;
import pathfinding.Point;
import pathfinding.PriorityQueue;
import pathfinding.Searcher;
import pathfinding.Waypoint;
import static pathfinding.Searcher.directions;
import tiles.assets.Barricade;
import tiles.assets.Door;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class CorridorBuilder{
    
    private final Area area;
    
    private final class WanderingCorridorAlgorithm extends Searcher{
        
        Point end;
        
        WanderingCorridorAlgorithm(){
            super(area.graph, area);
            addCheck = (from, to) -> /*to.currentCost > from.currentCost + to.movementCost &&*/ to.checked==null || !to.checked;
            frontier = null;
        }
        
        void setDestination(Point d){
            end = d;
            frontier = new PriorityQueue<>(p -> Distribution.getRandomInt(5, 15) +  p.currentCost + p.getOODistance(d));
        }
        
        @Override
        public void checkedFloodfill(Point start){
            graph.use();
            frontier.clear();
            start.currentCost = 0;
            frontier.add(start);
            int nx, ny;
            while(!frontier.isEmpty()){
                Point p = frontier.poll();
                for(Point.Direction dir : directions){
                    nx = p.x+dir.x;
                    ny = p.y+dir.y;
                    if(area.withinBounds(nx-1, ny-1)&&area.withinBounds(nx+1, ny+1)){
                        if(graph.map[ny][nx].equals(end)){
                            graph.map[ny][nx].cameFrom = p;
                            return;
                        }else if((area.map[ny][nx]==null||graph.map[ny][nx].isCorridor)&&addCheck.check(p, graph.map[ny][nx])){
                            graph.map[ny][nx].checked = true;
                            graph.map[ny][nx].cameFrom = p;
                            graph.map[ny][nx].currentCost = p.currentCost + graph.map[ny][nx].movementCost;
                            frontier.add(graph.map[ny][nx]);
                        }
                    }
                }
            }
        }
        
        List<Path> generatePaths(List<Waypoint> points, List<Path> carry){
            int s = Distribution.r.nextInt(points.size()),
                    e = Distribution.getRandomInt(0, points.size(), s);
            Waypoint start = points.get(s);
            setDestination(points.remove(e));
            checkedFloodfill(start);
            carry.add(graph.followTrail(end.x, end.y));
            if(points.size()==1) return carry;
            if(points.size()==2){
                setDestination(points.get(1));
                checkedFloodfill(points.get(0));
                carry.add(graph.followTrail(end.x, end.y));
                return carry;
            }
            if(Distribution.chance(1, 2)) points.remove(start);
            return generatePaths(points, carry);
        }
        
        Path generatePath(Point p1, Point p2){
            setDestination(p2);
            checkedFloodfill(p1);
            return graph.followTrail(p2.x, p2.y);
        }
        
    }
    
    /**
     * Creates a new instance.
     * @param a The Area.
     */
    public CorridorBuilder(Area a){
        area = a;
        if(area.graph==null) area.graph = new Graph(area);
    }
    
    private void extend(Point p, boolean hor){
        int x = p.x, y = p.y;
        if(!(area.map[y][x] instanceof Door||area.map[y][x] instanceof Barricade)){
            area.map[y][x] = Tile.floor(area.location);
            area.graph.map[y][x].isCorridor = true;
        }
        if(hor){
            if(area.map[y][x-1]==null){
                area.map[y][x-1] = Tile.wall(area.location);
                area.graph.map[y][x-1].isCorridor = true;
            }
            if(area.map[y][x+1]==null){
                area.map[y][x+1] = Tile.wall(area.location);
                area.graph.map[y][x+1].isCorridor = true;
            }
        }else{
            if(area.map[y-1][x]==null){
                area.map[y-1][x] = Tile.wall(area.location);
                area.graph.map[y-1][x].isCorridor = true;
            }
            if(area.map[y+1][x]==null){
                area.map[y+1][x] = Tile.wall(area.location);
                area.graph.map[y+1][x].isCorridor = true;
            }
        }
    }
    
    /**
     * Generates and builds all corridors in the Area.
     */
    public void build(){
        List<Path> paths = new WanderingCorridorAlgorithm().generatePaths(
                Arrays.asList(area.graph.waypoints).stream().filter(p -> !waypointReached(p)).collect(Collectors.toList()),
                new LinkedList<>());
        paths.stream().forEach((path) -> {
            buildCorridor(path);
        });
        fix();
        area.graph.initializeWaypoints();
    }
    
    /**
     * Builds a corridor out of a singular Path.
     * @param path
     */
    public void buildCorridor(Path path){
        boolean horizontal = Point.isHorizontal(path.get(0), path.get(1)), nowHorizontal;
        extend(path.get(1), horizontal);
        for(int n=1;n<path.size()-1;n++){
            nowHorizontal = Point.isHorizontal(path.get(n), path.get(1+n));
            extend(path.get(n+1), horizontal);
            if(nowHorizontal!=horizontal) fillGaps(path.get(n), horizontal);
            horizontal = nowHorizontal;
        }
        extend(path.get(path.size()-1), horizontal);
    }
    
    /**
     * Checks whether the given WayPoint has been reached by a corridor.
     * @param wp
     * @return
     */
    protected boolean waypointReached(Waypoint wp){
        return area.map[wp.y-1][wp.x]!=null&&area.map[wp.y-1][wp.x].treadable&&area.map[wp.y+1][wp.x]!=null&&area.map[wp.y+1][wp.x].treadable||
                area.map[wp.y][wp.x-1]!=null&&area.map[wp.y][wp.x-1].treadable&&area.map[wp.y][wp.x+1]!=null&&area.map[wp.y][wp.x+1].treadable;
    }
    
    private void fillGaps(Point p, boolean hor){
        if(area.map[p.y+1][p.x+1]==null){
            area.map[p.y+1][p.x+1] = Tile.wall(area.location);
            area.graph.map[p.y+1][p.x+1].isCorridor = true;
        }
        if(area.map[p.y-1][p.x-1]==null){
            area.map[p.y-1][p.x-1] = Tile.wall(area.location);
            area.graph.map[p.y-1][p.x-1].isCorridor = true;
        }
        if(area.map[p.y-1][p.x+1]==null){
            area.map[p.y-1][p.x+1] = Tile.wall(area.location);
            area.graph.map[p.y-1][p.x+1].isCorridor = true;
        }
        if(area.map[p.y+1][p.x-1]==null){
            area.map[p.y+1][p.x-1] = Tile.wall(area.location);
            area.graph.map[p.y+1][p.x-1].isCorridor = true;
        }
        if(hor){
            if(area.map[p.y-1][p.x]==null){
                area.map[p.y-1][p.x] = Tile.wall(area.location);
                area.graph.map[p.y-1][p.x].isCorridor = true;
            }
            if(area.map[p.y+1][p.x]==null){
                area.map[p.y+1][p.x] = Tile.wall(area.location);
                area.graph.map[p.y+1][p.x].isCorridor = true;
            }
        }else{
            if(area.map[p.y][p.x-1]==null){
                area.map[p.y][p.x-1] = Tile.wall(area.location);
                area.graph.map[p.y][p.x-1].isCorridor = true;
            }
            if(area.map[p.y][p.x+1]==null){
                area.map[p.y][p.x+1] = Tile.wall(area.location);
                area.graph.map[p.y][p.x+1].isCorridor = true;
            }
        }
    }
    
    private void fix(){
        Point waypoint = area.graph.getClosestWaypoint(
                area.startCoords[0], area.startCoords[1]);
        Searcher search = new Searcher(area.graph, area);
        WanderingCorridorAlgorithm corSearch = new WanderingCorridorAlgorithm();
        search.checkedFloodfill(new Point(area.startCoords[0], area.startCoords[1]));
        for(Point p : area.graph.waypoints){
            if(p.cameFrom==null) buildCorridor(corSearch.generatePath(waypoint, p));
        }
    }
    
}
