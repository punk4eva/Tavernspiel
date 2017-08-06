
package pathfinding;

import java.util.HashMap;

/**
 *
 * @author Adam Whittaker
 */
public class Waypoint extends Point{
    
    public HashMap<Waypoint, Path> pathsToWaypoints = new HashMap<>();
    
    public Waypoint(int x1, int y1){
        super(x1, y1);
    }
    
}
