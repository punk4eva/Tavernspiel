
package creatureLogic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class FieldOfView{
    
    private final Set<Integer[]> visible = new HashSet<>();
    protected int x, y;
    public int range;
    
    /**
     * Creates a new instance.
     * @param _x The x of the viewer
     * @param _y The x of the viewer
     * @param r The view distance of the viewer
     */
    public FieldOfView(int _x, int _y, int r){
        x = _x;
        y = _y;
        range = r;
    }
    
    /**
     * Updates what the viewer can see.
     * @param x_ The new x
     * @param y_ The new y
     * @param area The new Area
     */
    public void update(int x_, int y_, Area area){
        x = x_;
        y = y_;
        visible.clear();
        followGradient(x, y, area);
    }
    
    /**
     * Checks whether the given tile coordinates are visible.
     * @param x_ The x
     * @param y_ The y
     * @return
     */
    public boolean isVisible(int x_, int y_){
        return visible.stream().anyMatch(c -> c[0]==x&&c[1]==y);
    }    
    
    /**
     * Defines what is visible and invisible
     * @param x_ The x
     * @param y_ The y
     * @param area The Area
     */
    protected void followGradient(int x_, int y_, Area area){
        visible.add(new Integer[]{x_, y_});
        double inc = Math.atan(0.08D);
        List<LightRay> runners = new LinkedList<>();
        for(double theta=0;theta<2d*Math.PI;theta+=inc) runners.add(new LightRay(x_, y_, range, theta));
        while(!runners.isEmpty()){
            runners.removeIf(r -> r.fuel==0);
            runners.stream().forEach(r -> {
                Integer[] c = r.run(area);
                visible.add(c);
            });
        }
    }
    
    /**
     * This class represents a light ray from the viewer's eyes.
     */
    public static class LightRay{
        
        public int fuel, x, y;
        private double dx, dy, ix, iy;
        
        /**
         * Creates a new instance.
         * @param _x The x of the viewer.
         * @param _y The y of the viewer.
         * @param r The range of the viewer's vision.
         * @param theta The angle of inclination of the light ray.
         */
        public LightRay(int _x, int _y, int r, double theta){
            x = _x;
            y = _y;
            fuel = r;
            ix = Math.cos(theta);
            iy = Math.sin(theta);
        }
        
        /**
         * Moves forward one tile.
         * @param area The Area the LightRay is located in.
         * @return The new coordinates of the LightRay.
         */
        public Integer[] run(Area area){
            fuel--;
            dx+=ix;
            dy+=iy;
            if(dx>=1){
                dx--;
                x++;
            }else if(dx<=-1){
                x--;
                dx++;
            }
            if(dy>=1){
                dy--;
                y++;
            }else if(dy<=-1){
                y--;
                dy++;
            }
            if(!area.map[y][x].transparent) fuel = 0;
            return new Integer[]{x, y};
        }
        
    }
            
}
