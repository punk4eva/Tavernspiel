
package pathfinding.generation;

import java.util.LinkedList;
import java.util.List;
import level.Area;
import logic.Distribution;
import pathfinding.Graph;
import pathfinding.Point;
import pathfinding.Searcher;
import tiles.assets.Door;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 * 
 * This class builds a Maze in the given Area.
 */
public class MazeBuilder{
    
    private final Area area;
    private final int TLX, TLY, width, height;
    
    /**
     * Builds a maze in the given Area at the given coordinates with the given
     * width and height.
     * @param a
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public MazeBuilder(Area a, int x, int y, int w, int h){
        area = a;
        TLX = x;
        TLY = y;
        width = w;
        height = h;
        for(int cy=y;cy<height+y;cy++)
            for(int cx=x;cx<width+x;cx++) area.map[cy][cx] = Tile.wall(area.location, cx, cy);
        if(area.graph==null) area.graph = new Graph(area, null);
        else area.graph.reset();
        int dx = Distribution.r.nextInt(area.dimension.width/2-2)*2+3,
                dy = Distribution.r.nextInt(area.dimension.height/2-2)*2+3;
        new MazeAlgorithm().checkedFloodfill(area.graph.map[dy][dx]);
        area.graph = new Graph(area, null);
    }
    
    private final class MazeAlgorithm extends Searcher{
        
        MazeAlgorithm(){
            super(area.graph, area);
        }
        
        @Override
        public void checkedFloodfill(Point start){
            graph.reset();
            Point next;
            start.checked = true;
            int doors = Distribution.getRandomInt(2, (area.dimension.width+area.dimension.height)*7/50);
            while(true){
                next = getDirection(start);
                while(next==null){
                    if(start.cameFrom==null) return;
                    start = start.cameFrom;
                    next = getDirection(start);
                }
                next.cameFrom = start;
                next.checked = true;
                if(onEdge(next)){
                    if(doors>0){
                        doors--;
                        area.map[next.y][next.x] = new Door(area.location);
                    }
                    start = start.cameFrom;
                }else{
                    carve(start, next);
                    start = next;
                }
            }
        }

        private Point getDirection(Point p){
            List<Point> points = new LinkedList<>();
            if(withinBounds(p.x, p.y-2)&&(graph.map[p.y-2][p.x].checked==null||!graph.map[p.y-2][p.x].checked)) points.add(graph.map[p.y-2][p.x]);
            else if(onEdge(p.x, p.y-1)&&graph.map[p.y-1][p.x].checked==null) points.add(graph.map[p.y-1][p.x]);
            if(withinBounds(p.x, p.y+2)&&(graph.map[p.y+2][p.x].checked==null||!graph.map[p.y+2][p.x].checked)) points.add(graph.map[p.y+2][p.x]);
            else if(onEdge(p.x, p.y+1)&&graph.map[p.y+1][p.x].checked==null) points.add(graph.map[p.y+1][p.x]);
            if(withinBounds(p.x-2, p.y)&&(graph.map[p.y][p.x-2].checked==null||!graph.map[p.y][p.x-2].checked)) points.add(graph.map[p.y][p.x-2]);
            else if(onEdge(p.x-1, p.y)&&graph.map[p.y][p.x-1].checked==null) points.add(graph.map[p.y][p.x-1]);
            if(withinBounds(p.x+2, p.y)&&(graph.map[p.y][p.x+2].checked==null||!graph.map[p.y][p.x+2].checked)) points.add(graph.map[p.y][p.x+2]);
            else if(onEdge(p.x+1, p.y)&&graph.map[p.y][p.x+1].checked==null) points.add(graph.map[p.y][p.x+1]);
            return points.isEmpty()? null : points.get(Distribution.r.nextInt(points.size()));
        }
        
    }
    
    private void carve(Point s, Point n){
        if(s.x<n.x){
            area.map[s.y][s.x+1] = Tile.floor(area.location);
            area.map[s.y][n.x] = Tile.floor(area.location);
        }else if(s.x>n.x){
            area.map[s.y][s.x-1] = Tile.floor(area.location);
            area.map[s.y][n.x] = Tile.floor(area.location);
        }else if(s.y<n.y){
            area.map[s.y+1][s.x] = Tile.floor(area.location);
            area.map[n.y][s.x] = Tile.floor(area.location);
        }else{
            area.map[s.y-1][s.x] = Tile.floor(area.location);
            area.map[n.y][s.x] = Tile.floor(area.location);
        }
    }
    
    private boolean withinBounds(int x, int y){
        return x>=TLX&&x<TLX+width&&y>=TLY&&y<TLY+height;
    }
    
    private boolean onEdge(Point p){
        return TLX==p.x||p.x==TLX+width-1||TLY==p.y||p.y==TLY+height-1;
    }
    
    private boolean onEdge(int x, int y){
        return TLX==x||x==TLX+width-1||TLY==y||y==TLY+height-1;
    }
    
}
