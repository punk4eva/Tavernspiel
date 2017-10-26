
package pathfinding;

import java.util.LinkedList;
import java.util.List;
import level.Area;
import logic.Distribution;
import tiles.Door;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class MazeBuilder{
    
    private final Area area;
    private final int TLX, TLY, width, height;
    
    public MazeBuilder(Area a, int x, int y, int w, int h){
        area = a;
        TLX = x;
        TLY = y;
        width = w;
        height = h;
        for(int cy=y;cy<height+y;cy++)
            for(int cx=x;cx<width+x;cx++) area.map[cy][cx] = Tile.wall(area.location);
        if(area.graph==null) area.graph = new Graph(area);
        new MazeAlgorithm().floodfill(area.graph.map[Distribution.r.nextInt(area.dimension.height/2)]
                [Distribution.r.nextInt(area.dimension.width/2)]);
    }
    
    private final class MazeAlgorithm extends Searcher{
        
        MazeAlgorithm(){
            super(area.graph);
        }
        
        @Override
        public void floodfill(Point start){
            graph.use();
            Point next;
            start.checked = true;
            int doors = Distribution.getRandomInt(2, (area.dimension.width+area.dimension.height)*7/50);
            while(true){
                next = getDirection(start);
                while(next==null){
                    if(start.cameFrom==null){
                        for(int n=0;n<2;n++) switch(Distribution.r.nextInt(4)){
                            case 0: area.map[TLY][TLX+Distribution.r.nextInt(area.dimension.width-2)+1] = new Door(area.location);
                                break;
                            case 1: area.map[TLY+height-1][TLX+Distribution.r.nextInt(area.dimension.width-2)+1] = new Door(area.location);
                                break;
                            case 2: area.map[TLY+Distribution.r.nextInt(area.dimension.height-2)+1][TLX] = new Door(area.location);
                                break;
                            default: area.map[TLY+Distribution.r.nextInt(area.dimension.height-2)+1][TLX+width-1] = new Door(area.location);
                                break;
                        }
                        return;
                    }
                    start = start.cameFrom;
                    next = getDirection(start);
                }
                carve(start, next);
                if(onEdge(next)){
                    doors--;
                    area.map[next.y][next.x] = new Door(area.location);
                    if(doors==0) break;
                    checkAll(next);
                }
                next.cameFrom = start;
                next.checked = true;
                start = next;
            }
        }

        private Point getDirection(Point p){
            List<Point> points = new LinkedList<>();
            if(withinBounds(p.x, p.y-2)&&(graph.map[p.y-2][p.x].checked==null||!graph.map[p.y-2][p.x].checked)) points.add(graph.map[p.y-2][p.x]);
            if(withinBounds(p.x, p.y+2)&&(graph.map[p.y+2][p.x].checked==null||!graph.map[p.y+2][p.x].checked)) points.add(graph.map[p.y+2][p.x]);
            if(withinBounds(p.x-2, p.y)&&(graph.map[p.y][p.x-2].checked==null||!graph.map[p.y][p.x-2].checked)) points.add(graph.map[p.y][p.x-2]);
            if(withinBounds(p.x+2, p.y)&&(graph.map[p.y][p.x+2].checked==null||!graph.map[p.y][p.x+2].checked)) points.add(graph.map[p.y][p.x+2]);
            return points.isEmpty()? null : points.get(Distribution.r.nextInt(points.size()));
        }
        
        private void checkAll(Point p){
            if(withinBounds(p.x+2, p.y)) graph.map[p.y][p.x+2].checked = true;
            if(withinBounds(p.x-2, p.y)) graph.map[p.y][p.x-2].checked = true;
            if(withinBounds(p.x, p.y+2)) graph.map[p.y+2][p.x].checked = true;
            if(withinBounds(p.x, p.y-2)) graph.map[p.y-2][p.x].checked = true;
        }
        
    }
    
    private void carve(Point s, Point n){
        if(s.x<n.x){
            area.map[s.y][s.x+1] = new Tile("floor", area.location, true, false);
            area.map[s.y][n.x] = new Tile("floor", area.location, true, false);
            return;
        }
        if(s.x>n.x){
            area.map[s.y][s.x-1] = new Tile("floor", area.location, true, false);
            area.map[s.y][n.x] = new Tile("floor", area.location, true, false);
            return;
        }
        if(s.y<n.y){
            area.map[s.y+1][s.x] = new Tile("floor", area.location, true, false);
            area.map[n.y][s.x] = new Tile("floor", area.location, true, false);
            return;
        }
        area.map[s.y-1][s.x] = new Tile("floor", area.location, true, false);
        area.map[n.y][s.x] = new Tile("floor", area.location, true, false);
    }
    
    private boolean withinBounds(int x, int y){
        return x>=TLX&&x<TLX+width&&y>=TLY&&y<TLY+height;
    }
    
    private boolean onEdge(Point p){
        return TLX==p.x||p.x==TLX+width-1||TLY==p.y||p.y==TLY+height-1;
    }
    
}
