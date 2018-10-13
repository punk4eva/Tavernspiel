
package pathfinding;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Point from which other destinations can be located.
 */
public class Waypoint extends Point{
    
    private final static long serialVersionUID = 58846665532899L;
    
    /**
     * Creates an instance.
     * @param x1
     * @param y1
     */
    public Waypoint(int x1, int y1){
        super(x1, y1);
    }
    
}
