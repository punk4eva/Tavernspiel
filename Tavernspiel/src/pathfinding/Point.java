
package pathfinding;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class Point implements Serializable{
    
    private final static long serialVersionUID = 77292912;
    
    Boolean checked = false; //null if not treadable
    public final int x, y;
    int movementCost = 1; //The cost to step into this tile.
    Point cameFrom = null; //null if starting point. 
    int currentCost = Integer.MAX_VALUE;
    boolean isCorridor = false;
    public static enum Direction{
        NORTH(2, x->x, y->y-1),
        EAST(5, x->x-1, y->y),
        SOUTH(7, x->x, y->y+1), 
        WEST(4, x->x+1, y->y);
        
        int code;
        public final xDir x;
        public final yDir y;
        Direction(int c, xDir xd, yDir yd){
            code = c;
            x = xd;
            y = yd;
        }
        
        public interface xDir{
            int update(int x);
        }
        public interface yDir{
            int update(int y);
        }
        
        public Direction polar(){
            switch(this){
                case NORTH: return SOUTH;
                case SOUTH: return NORTH;
                case WEST: return EAST;
                case EAST: return WEST;
                default: return null;
            }
        }
    }
    public static enum ExtendedDirection{
        NORTHWEST(1, x->x-1, y->y-1),
        NORTH(2, x->x, y->y-1),
        NORTHEAST(3, x->x+1, y->y-1),
        EAST(5, x->x-1, y->y),
        SOUTHEAST(8, x->x+1, y->y+1),
        SOUTH(7, x->x, y->y+1), 
        SOUTHWEST(6, x->x+1, y->y+1),
        WEST(4, x->x+1, y->y);
        
        int code;
        public final xDir x;
        public final yDir y;
        ExtendedDirection(int c, xDir xd, yDir yd){
            code = c;
            x = xd;
            y = yd;
        }
        
        public interface xDir{
            int update(int x);
        }
        public interface yDir{
            int update(int y);
        }
        
        public ExtendedDirection polar(){
            switch(this){
                case NORTHWEST: return SOUTHEAST;
                case SOUTHEAST: return NORTHWEST;
                case NORTH: return SOUTH;
                case SOUTH: return NORTH;
                case WEST: return EAST;
                case EAST: return WEST;
                case NORTHEAST: return SOUTHWEST;
                case SOUTHWEST: return NORTHEAST;
                default: return null;
            }
        }
    }
    
    public Point(int x1, int y1){
        x = x1;
        y = y1;
    }
    
    public Point(int x1, int y1, Boolean c){
        x = x1;
        y = y1;
        checked = c;
    }
    
    public Point(int x1, int y1, Boolean c, boolean corr){
        x = x1;
        y = y1;
        isCorridor = corr;
        checked = c;
    }
    
    
    public int getOODistance(Point p){
        return Math.max(Math.abs(x-p.x), Math.abs(y-p.y));
    }
    
    public int getOODistance(int x1, int y1){
        return Math.max(Math.abs(x-x1), Math.abs(y-y1));
    }
    
    public boolean equals(Point p){
        return x==p.x&&y==p.y;
    }
    
    public int[] getCoords(){
        return new int[]{x, y};
    }
    
    public void paint(Graphics g, int _x, int _y, Area area){
        /*if(isCorridor) g.setColor(Color.BLUE);
        else if(checked==null) g.setColor(Color.WHITE);
        else if(cameFrom==null) g.setColor(Color.YELLOW);
        else if(checked) g.setColor(Color.RED);
        else g.setColor(Color.BLACK);*/
        try{ if(area.map[y][x].transparent){
            g.setColor(Color.BLUE);
            g.fillOval(_x+4, _y+4, 8, 8);
        }}catch(NullPointerException e){
            g.setColor(Color.RED);
            g.fillOval(_x+4, _y+4, 8, 8);
        }
    }
    
}
