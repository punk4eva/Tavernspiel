
package creatureLogic;

import level.Area;

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
        for(int ny=y-range;ny<=y+range;ny++){
            for(int nx=x-range;nx<=x+range;nx++){
                try{
                    if(followGradient(nx, ny, area)) map[ny][nx] = 2;
                }catch(ArrayIndexOutOfBoundsException e){}
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
