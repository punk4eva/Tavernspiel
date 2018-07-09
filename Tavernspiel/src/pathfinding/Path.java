
package pathfinding;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Path of Points.
 */
public class Path implements Iterable<Point>, RandomAccess{
    
    public Point[] points;
    
    /**
     * Creates an instance.
     * @param ps The collection of Points forming this Path.
     */
    public Path(Point... ps){
        points = ps;
    }
    
    /**
     * Creates a new Path equal to the reverse of this Path.
     * @return
     */
    public Path reverse(){
        Point[] ret = new Point[points.length];
        for(int n=0;n<points.length;n++){
            ret[ret.length-n-1] = points[n];
        }
        return new Path(ret);
    }
    
    /**
     * Concatenates this Path with another and returns the result.
     * @param p
     * @return
     */
    public Path concatenate(Path p){
        List<Point> ret = Arrays.asList(points);
        ret.addAll(Arrays.asList(p.points).subList(1, p.points.length));
        return new Path(ret.toArray(new Point[ret.size()]));
    }
    
    /**
     * Concatenates another Path with this one and returns the result.
     * @param p
     * @return
     */
    public Path concatenateBefore(Path p){
        List<Point> ret = Arrays.asList(p.points);
        ret.addAll(Arrays.asList(points).subList(1, points.length));
        return new Path(ret.toArray(new Point[ret.size()]));
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
     * Paints a debug overlay of this Path.
     * @param g The Graphics.
     * @param focusX
     * @param focusY
     */
    public void paint(Graphics g, int focusX, int focusY){
        g.setColor(ConstantFields.frontColor);
        for(Point point : points){
            g.fillOval(point.x*16+focusX+4, point.y*16+focusY+4, 8, 8);
        }
    }
    
    @Override
    public Iterator<Point> iterator(){
        return new Iterator<Point>(){
            
            private int currentIndex = 0;
            
            @Override
            public boolean hasNext(){
                return currentIndex<points.length;
            }

            @Override
            public Point next(){
                return points[currentIndex++];
            }
            
        };
    }
    
}
