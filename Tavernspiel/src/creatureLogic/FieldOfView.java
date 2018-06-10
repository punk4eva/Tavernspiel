
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
    
    public FieldOfView(int _x, int _y, int r){
        x = _x;
        y = _y;
        range = r;
    }
    
    public void update(int x_, int y_, Area area){
        x = x_;
        y = y_;
        visible.clear();
        followGradient(x, y, area);
    }
    
    public boolean isVisible(int x_, int y_){
        return visible.stream().anyMatch(c -> c[0]==x&&c[1]==y);
    }    
    
    public void followGradient(int x_, int y_, Area area){
        visible.add(new Integer[]{x_, y_});
        double inc = Math.atan(0.08D);
        List<ViewRunner> runners = new LinkedList<>();
        for(double theta=0;theta<2d*Math.PI;theta+=inc) runners.add(new ViewRunner(x_, y_, range, theta));
        while(!runners.isEmpty()){
            runners.removeIf(r -> r.fuel==0);
            runners.stream().forEach(r -> {
                Integer[] c = r.run(area);
                visible.add(c);
            });
        }
    }
    
    public static class ViewRunner{
        
        public int fuel, x, y;
        private double dx, dy, ix, iy;
        
        public ViewRunner(int _x, int _y, int r, double theta){
            x = _x;
            y = _y;
            fuel = r;
            ix = Math.cos(theta);
            iy = Math.sin(theta);
        }
        
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
