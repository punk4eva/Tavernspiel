
package creatureLogic;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class VisibilityOverlay extends FieldOfView{
    
    //0: unexplored
    //1: explored
    //2: visible
    private final int[][] map;
    private final Dimension dimension;

    public VisibilityOverlay(int x, int y, int range, Area area){
        super(x, y, range);
        map = new int[area.dimension.height][area.dimension.width];
        dimension = area.dimension;
    }
    
    @Override
    public void update(int x_, int y_, Area area){
        x = x_;
        y = y_;
        clearVisible();
        followGradient(x, y, area);
    }
    
    @Override
    public synchronized void followGradient(int x_, int y_, Area area){
        List<Double[]> blockedRanges = new LinkedList<>();
        double increment = /*0.1*Math.PI/((double)range)*/Math.PI/60.0;
        double st = -1;
        map[y_][x_] = 2;
        for(int r=1;r<=range;r++){
            for(double theta=0, max=2.0*Math.PI;theta<max;theta+=increment){
                if(st!=-1){
                    try{
                        Integer c[] = polarToCartesian(r, theta, x_, y_);
                        if(area.map[c[1]][c[0]].transparent){
                            if(st>theta){
                                blockedRanges.add(new Double[]{st, 2*Math.PI});
                                blockedRanges.add(new Double[]{0.0, theta});
                            }else blockedRanges.add(new Double[]{st, theta});
                            st = -1;
                        }                    
                    }catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
                    continue;
                }
                if(blocked(theta, blockedRanges)) continue;
                try{
                    Integer c[] = polarToCartesian(r, theta, x_, y_);
                    map[c[1]][c[0]] = 2;
                    if(!area.map[c[1]][c[0]].transparent){
                        st = theta;
                    }
                }catch(ArrayIndexOutOfBoundsException | NullPointerException e){}
            }
        }
    }
    
    private void clearVisible(){
        for(int ny=0;ny<dimension.height;ny++)
            for(int nx=0;nx<dimension.width;nx++)
                if(map[ny][nx]==2) map[ny][nx] = 1;
    }
    
    @Override
    public boolean isVisible(int x_, int y_){
        return map[y_][x_]==2;
    }
    
    public boolean isUnexplored(int x_, int y_){
        return map[y_][x_]==0;
    }
    
    public boolean isExplored(int x_, int y_){
        return map[y_][x_]==1;
    }
    
}
