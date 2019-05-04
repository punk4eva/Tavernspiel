
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
    
    public Boolean checked = false; //null if not treadable
    public final int x, y;
    public int movementCost = 1; //The cost to step into this tile.
    public Point cameFrom = null; //null if starting point. 
    public int currentCost = Integer.MAX_VALUE;
    public boolean isCorridor = false;
    public int roomNum = -1, number = -1;
    /**
     * This Enum represents all the cardinal directions.
     */
    public static enum Direction{
        NORTH(2, 0, -1),
        EAST(5, -1, 0),
        SOUTH(7, 0, 1), 
        WEST(4, 1, 0);
        
        int code;
        public final int x;
        public final int y;
        Direction(int c, int xd, int yd){
            code = c;
            x = xd;
            y = yd;
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
    /**
     * This Enum represents all the ordino-cardinal directions.
     */
    public static enum ExtendedDirection{
        NORTH(2, 0, -1),
        EAST(5, -1, 0),
        SOUTH(7, 0, 1), 
        WEST(4, 1, 0),
        NORTHWEST(1, -1, -1),
        NORTHEAST(3, 1, -1),
        SOUTHEAST(8, 1, 1),
        SOUTHWEST(6, -1, 1);
        
        int code;
        public final int x;
        public final int y;
        ExtendedDirection(int c, int xd, int yd){
            code = c;
            x = xd;
            y = yd;
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
    
    /**
     * Creates an instance.
     * @param x1
     * @param y1
     */
    public Point(int x1, int y1){
        x = x1;
        y = y1;
    }
    
    /**
     * Creates an instance.
     * @param x1
     * @param y1
     * @param c Whether the Point has been checked by a Pathfinding algorithm.
     */
    public Point(int x1, int y1, Boolean c){
        x = x1;
        y = y1;
        checked = c;
    }
    
    /**
     * Creates an instance.
     * @param x1
     * @param y1
     * @param c Whether the Point has been checked by a Pathfinding algorithm.
     * @param corr Whether this Point is part of a corridor.
     */
    public Point(int x1, int y1, Boolean c, boolean corr){
        x = x1;
        y = y1;
        isCorridor = corr;
        checked = c;
    }
    
    /**
     * Checks whether two Points are horizontally aligned.
     * @param prev
     * @param next
     * @return
     */
    public static boolean isHorizontal(Point prev, Point next){
        return prev.x==next.x;
    }
    
    /**
     * Resets The pathfinding aspects of this Point.
     */
    public void resetPathfinding(){
        if(checked!=null) checked = false;
        cameFrom = null;
        currentCost = Integer.MAX_VALUE;
    }
    
    /**
     * Gets the Manhattan distance from another Point.
     * @param p
     * @return
     */
    public int getOMDistance(Point p){
        return Math.max(Math.abs(x-p.x), Math.abs(y-p.y));
    }
    
    /**
     * Gets the Manhattan distance from other coordinates.
     * @param x1
     * @param y1
     * @return
     */
    public int getOMDistance(int x1, int y1){
        return Math.max(Math.abs(x-x1), Math.abs(y-y1));
    }
    
    /**
     * Tests if the two Points are equal by their coordinates.
     * @param p
     * @return
     */
    public boolean equals(Point p){
        return x==p.x&&y==p.y;
    }
    
    /**
     * Debug method
     * @param g
     * @param _x
     * @param _y
     * @param area
     */
    public void paint(Graphics g, int _x, int _y, Area area){
        /*if(cameFrom==null && this instanceof Waypoint) g.setColor(Color.WHITE);
        else if(this instanceof Waypoint) g.setColor(Color.BLUE);
        else if(cameFrom==null) g.setColor(Color.BLACK);
        else if(checked==null) g.setColor(Color.RED);
        else g.setColor(Color.YELLOW);
        g.fillOval(_x+4, _y+4, 8, 8);*/
        /*if(isCorridor){
            g.setColor(Color.BLUE);
            g.fillOval(_x+4, _y+4, 8, 8);
        }*/
    }
    
}
