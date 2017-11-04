
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
        List<Double[]> blockedRanges = new LinkedList<>();
        double increment = 0.5/range;
        double st = -1;
        for(int r=1;r<=range;r++){
            for(double theta=0, max=2*Math.PI;theta<max;theta+=increment){
                if(st!=-1){
                    try{
                        Integer c[] = polarToCartesian(r, theta, x_, y_);
                        if(area.map[c[1]][c[0]].transparent){
                            visible.add(c);
                            blockedRanges.add(new Double[]{st, theta});
                            st = -1;
                        }                    
                    }catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
                    continue;
                }
                if(blocked(theta, blockedRanges)) continue;
                try{
                    Integer c[] = polarToCartesian(r, theta, x_, y_);
                    visible.add(c);
                    if(!area.map[c[1]][c[0]].transparent){
                        st = theta;
                    }
                }catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }
    
    protected Integer[] polarToCartesian(int r, double theta, int x_, int y_){
        return new Integer[]{x_+(int)Math.round(r*Math.cos(theta)), y_+(int)Math.round(r*Math.sin(theta))};    
    }
    
    protected boolean blocked(double theta, List<Double[]> block){
        return block.stream().anyMatch(c -> theta>=c[0]&&theta<=c[1]);
    }
            
}
