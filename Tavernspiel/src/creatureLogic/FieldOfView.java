
package creatureLogic;

import java.util.LinkedList;
import java.util.List;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class FieldOfView{
    
    private final List<Integer[]> visible = new LinkedList<>();
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
        for(int ny=y-range;ny<=y+range;ny++){
            for(int nx=x-range;nx<=x+range;nx++){
                try{
                    if(followGradient(nx, ny, area)) visible.add(new Integer[]{nx, ny});
                }catch(ArrayIndexOutOfBoundsException e){}
            }
        }
    }
    
    public boolean isVisible(int x_, int y_){
        return visible.stream().anyMatch(c -> c[0]==x&&c[1]==y);
    }
    
    protected boolean followGradient(double x_, double y_, Area area){
        double gradient = (y_ - y) / (x_ - x), nx = x, ny = y;
        if(x_ == x){
            if(y < y_){
                while(true){
                    if((int) ny == y_){
                        return true;
                    }
                    if(area.map[(int) ny][(int) nx] != null && !area.map[(int) ny][(int) nx].transparent){
                        return false;
                    }
                    ny++;
                }
            }else{
                while(true){
                    if((int) ny == y_){
                        return true;
                    }
                    if(area.map[(int) ny][(int) nx] != null && !area.map[(int) ny][(int) nx].transparent){
                        return false;
                    }
                    ny--;
                }
            }
        }else if(x_ > x){
            while(true){
                if((int) nx == x_ && (int) ny == y_){
                    return true;
                }
                if(area.map[(int) ny][(int) nx] != null && !area.map[(int) ny][(int) nx].transparent){
                    return false;
                }
                if(gradient < 1){
                    nx++;
                    ny += gradient;
                }else{
                    ny++;
                    nx += 1.0 / gradient;
                }
            }
        }else{
            while(true){
                if((int) nx == x_ && (int) ny == y_){
                    return true;
                }
                if(area.map[(int) ny][(int) nx] != null && !area.map[(int) ny][(int) nx].transparent){
                    return false;
                }
                if(gradient < 1){
                    nx--;
                    ny -= gradient;
                }else{
                    ny--;
                    nx -= 1.0 / gradient;
                }
            }
        }
    }
            
}
