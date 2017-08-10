
package pathfinding;

import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class Point extends Object{
    
    Boolean checked = false; //null if not treadable
    int x, y;
    int movementCost = 1; //The cost to step into this tile.
    Point cameFrom = null; //null if starting point. 
    int currentCost = Integer.MAX_VALUE;
    static enum Direction{
        START(0, x->x, y->y),
        UNCHECKED(-1, x->-1, y->-1),
        NORTHWEST(1, x->x-1, y->y-1),
        NORTH(2, x->x, y->y-1),
        NORTHEAST(3, x->x+1, y->y-1),
        EAST(5, x->x-1, y->y),
        SOUTHEAST(8, x->x+1, y->y+1),
        SOUTH(7, x->x, y->y+1), 
        SOUTHWEST(6, x->x+1, y->y+1),
        WEST(4, x->x-1, y->y);
        
        int code;
        xDir x;
        yDir y;
        Direction(int c, xDir xd, yDir yd){
            code = c;
            x = xd;
            y = yd;
        }
        
        interface xDir{
            int update(int x);
        }
        interface yDir{
            int update(int y);
        }
        
        public Direction polar(){
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
    
    
    public int getOODistance(Point p){
        return Math.max(Math.abs(x-p.x), Math.abs(y-p.y));
    }
    
    public int getOODistance(int x1, int y1){
        return Math.max(Math.abs(x-x1), Math.abs(y-y1));
    }
    
    public boolean equals(Point p){
        return x==p.x&&y==p.y;
    }
    
}