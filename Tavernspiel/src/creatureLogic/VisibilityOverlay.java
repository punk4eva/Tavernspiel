
package creatureLogic;

import java.util.LinkedList;
import java.util.List;
import level.Area;
import pathfinding.Point.ExtendedDirection;

/**
 *
 * @author Adam Whittaker
 */
public class VisibilityOverlay extends FieldOfView{
    
    //0: unexplored
    //1: explored
    //2: visible
    private int[][] map;

    public VisibilityOverlay(int x, int y, int range, Area area){
        super(x, y, range);
        map = new int[area.map.length][area.map[0].length];
    }
    
    @Override
    public void update(int x_, int y_, Area area){
        x = x_;
        y = y_;
        clearVisible();
        followGradient(x, y, area);
    }
    
    @Override
    public void followGradient(int x_, int y_, Area area){
        List<Double[]> blockedRanges = new LinkedList<>();
        double increment = 0.125/((double)range);
        double st = -1;
        map[y_][x_] = 2;
        for(int r=1;r<=range;r++){
            for(double theta=0, max=2*Math.PI;theta<max;theta+=increment){
                if(st!=-1){
                    try{
                        Integer c[] = polarToCartesian(r, theta, x_, y_);
                        if(area.map[c[1]][c[0]].transparent){
                            blockedRanges.add(new Double[]{st, theta});
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
        for(int ny=0;ny<map.length;ny++)
            for(int nx=0;nx<map[0].length;nx++)
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
