
package listeners;

import level.Area;

/**
 *
 * @author Adam Whittaker
 * 
 * @Delete May be sub-optimal.
 */
public class AreaEvent{
    
    private final String action;
    private int x;
    private int y;
    private Area area;
    
    public AreaEvent(String act, Area a){
        action = act;
        area = a;
    }
    
    public AreaEvent(String act, Area a, int nx, int ny){
        action = act;
        area = a;
        x = nx;
        y = ny;
    }
    
    public String getAction(){
        return action;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void notifyEvent(){
        area.areaActedUpon(this);
    }
    
    public Area getArea(){
        return area;
    }
    
    public void setXY(int nx, int ny){
        x = nx;
        y = ny;
    }

    public void setArea(Area ar){
        area = ar;
    }
    
}
