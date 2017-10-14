
package pathfinding;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import level.Area;
import logic.Distribution;
import static pathfinding.Searcher.directions;
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
            super(area.graph);
            addCheck = (from, to) -> to.currentCost > from.currentCost + to.movementCost/* && to.checked!=null && !to.checked*/;
            frontier = null;
        }
        
        void setDestination(Point d){
            end = d;
            frontier = new PriorityQueue<>(p -> Distribution.getRandomInt(5, 15) +  p.currentCost + p.getOODistance(d));
        }
        
        @Override
        public void floodfill(Point start){
            graph.use();
            frontier.clear();
            start.currentCost = 0;
            frontier.add(start);
            int nx, ny;
            while(!frontier.isEmpty()){
                Point p = frontier.poll();
                for(Point.Direction dir : directions){
                    nx = dir.x.update(p.x);
                    ny = dir.y.update(p.y);
                    if(area.withinBounds(nx, ny)){
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
        
        List<Path> generatePaths(List<Waypoint> points, List<Waypoint> cleared, List<Path> paths){
            int n = Distribution.r.nextInt(points.size());
            Waypoint start = points.remove(n);
            if(points.isEmpty()){
                setDestination(cleared.get(Distribution.r.nextInt(cleared.size()-1)));
                floodfill(start);
                Path pa = graph.followTrail(end.x, end.y);
                if(pa.points.length!=1) paths.add(pa);
                return paths;
            }else if(points.size()==2){
                Waypoint dest = cleared.get(Distribution.r.nextInt(cleared.size()));
                setDestination(dest);
                floodfill(start);
                Path pa = graph.followTrail(end.x, end.y);
                if(pa.points.length!=1) paths.add(pa);
                return paths;
            }
            Waypoint dest = points.get(Distribution.getRandomInt(0, points.size(), n));
            cleared.add(start);
            setDestination(dest);
            floodfill(start);
            Path pa = graph.followTrail(end.x, end.y);
            if(pa.points.length!=1) paths.add(pa);
            return generatePaths(points, cleared, paths);
        }
        
    }
    
    public CorridorBuilder(Area a){
        area = a;
        if(area.graph==null) area.graph = new Graph(area);
    }
    
    private void extend(Point p, boolean hor){
        int x = p.x, y = p.y;
        if(Distribution.chance(1, 10)) area.map[y][x] = new Tile("decofloor", area.location);
        else area.map[y][x] = new Tile("floor", area.location);
        area.graph.map[y][x].isCorridor = true;
        if(hor){
            if(area.map[y][x-1]!=null&&area.map[y][x-1].treadable) return;
            if(Distribution.chance(1, 10)) area.map[y][x-1] = new Tile("specwall", area.location);
            else area.map[y][x-1] = new Tile("wall", area.location);
            if(Distribution.chance(1, 10)) area.map[y][x+1] = new Tile("specwall", area.location);
            else area.map[y][x+1] = new Tile("wall", area.location);
            area.graph.map[y][x-1].isCorridor = true;
            area.graph.map[y][x+1].isCorridor = true;
        }else{
            if(area.map[y-1][x]!=null&&area.map[y-1][x].treadable) return;
            if(Distribution.chance(1, 10)) area.map[y-1][x] = new Tile("specwall", area.location);
            else area.map[y-1][x] = new Tile("wall", area.location);
            if(Distribution.chance(1, 10)) area.map[y+1][x] = new Tile("specwall", area.location);
            else area.map[y+1][x] = new Tile("wall", area.location);
            area.graph.map[y-1][x].isCorridor = true;
            area.graph.map[y+1][x].isCorridor = true;
        }
    }
    
    public void build(){
        List<Path> paths = new WanderingCorridorAlgorithm().generatePaths(
                Arrays.asList(area.graph.waypoints).stream().filter(p -> !waypointReached(p)).collect(Collectors.toList()),
                new LinkedList<>(), new LinkedList<>());
        paths.stream().forEach((p) -> {
            System.out.println(p.points.length);
        });
        paths.stream().forEach((path) -> {
            buildCorridor(path);
        });
    }
    
    public void buildCorridor(Path path){
        boolean horizontal = Path.isHorizontal(path.points[0], path.points[1]), nowHorizontal;
        extend(path.points[1], horizontal);
        for(int n=1;n<path.points.length-1;n++){
            nowHorizontal = Path.isHorizontal(path.points[n], path.points[n+1]);
            extend(path.points[n+1], horizontal);
            if(nowHorizontal!=horizontal) fillCorners(path.points[n]);
            horizontal = nowHorizontal;
        }
        extend(path.points[path.points.length-1], horizontal);
    }
    
    protected boolean waypointReached(Waypoint wp){
        return area.map[wp.y-1][wp.x]!=null&&area.map[wp.y-1][wp.x].treadable&&area.map[wp.y+1][wp.x]!=null&&area.map[wp.y+1][wp.x].treadable||
                area.map[wp.y][wp.x-1]!=null&&area.map[wp.y][wp.x-1].treadable&&area.map[wp.y][wp.x+1]!=null&&area.map[wp.y][wp.x+1].treadable;
    }
    
    private void fillCorners(Point p){
        area.map[p.y+1][p.x+1] = new Tile("wall", area.location);
        area.map[p.y-1][p.x-1] = new Tile("wall", area.location);
        area.map[p.y-1][p.x+1] = new Tile("wall", area.location);
        area.map[p.y+1][p.x-1] = new Tile("wall", area.location);
        area.graph.map[p.y+1][p.x+1].isCorridor = true;
        area.graph.map[p.y-1][p.x-1].isCorridor = true;
        area.graph.map[p.y-1][p.x+1].isCorridor = true;
        area.graph.map[p.y+1][p.x-1].isCorridor = true;
    }
    
}
