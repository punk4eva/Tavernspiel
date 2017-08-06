
package pathfinding;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 */
public class Path{
    
    public Point[] points;
    private int incrementor = 0;
    
    public Path(Point[] ps){
        points = ps;
    }
    
    public Point next(){
        return points[(incrementor++)];
    }
    
    public Path reverse(){
        Point[] ret = new Point[points.length];
        for(int n=0;n<points.length;n++){
            ret[ret.length-n-1] = points[n];
        }
        return new Path(ret);
    }
    
    public Path concatenate(Path p){
        List<Point> ret = Arrays.asList(points);
        ret.addAll(Arrays.asList(p.points).subList(1, p.points.length));
        return new Path(ret.toArray(new Point[ret.size()]));
    }
    
    public Path concatenateBefore(Path p){
        List<Point> ret = Arrays.asList(p.points);
        ret.addAll(Arrays.asList(points).subList(1, points.length));
        return new Path(ret.toArray(new Point[ret.size()]));
    }
    
}
