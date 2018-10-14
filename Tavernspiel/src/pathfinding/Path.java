
package pathfinding;

import creatureLogic.VisibilityOverlay;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Path of Points.
 */
public class Path extends ArrayList<Point>{
    
    /**
     * Creates an instance.
     * @param ps The array of Points forming this Path.
     */
    public Path(Point... ps){
        super(Arrays.asList(ps));
    }
    
    /**
     * Creates an instance.
     * @param ps The collection of Points forming this Path.
     */
    public Path(Collection<Point> ps){
        super(ps);
    }
    
    /**
     * Condenses an iterator of Points into a Path.
     * @param iter The Iterator.
     */
    public Path(Iterator<Point> iter){
        super();
        while(iter.hasNext()) add(iter.next());
    }
    
    /**
     * Concatenates this Path with another and returns the result.
     * @param path
     */
    public void concatenate(Path path){
        addAll(path.subList(1, size()));
    }

    /**
     * Paints a debug overlay of this Path.
     * @param g The Graphics.
     * @param focusX
     * @param focusY
     * @Delete
     */
    public void paint(Graphics g, int focusX, int focusY){
        g.setColor(ConstantFields.frontColor);
        forEach((point) -> {
            g.fillOval(point.x*16+focusX+4, point.y*16+focusY+4, 8, 8);
        });
    }
    
    /**
     * Cuts the Path down to the nearest Waypoint.
     */
    public void cutToWaypoint(){
        for(int n = size()-1;n>=0;n--) if(get(n) instanceof Waypoint){
            removeRange(n+1, size());
            break;
        }
    }
    
    /**
     * Returns whether this Path has been discovered by the player.
     * @param fov The FieldOfView.
     * @return
     */
    public boolean isDiscovered(VisibilityOverlay fov){
        return stream().allMatch(p -> fov.map[p.y][p.x]!=0);
    }
    
}
