
package listeners;

import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class AreaEvent{
    
    private final String action;
    private int x;
    private int y;
    private final Area area;
    
    public AreaEvent(String act, Area a){
        action = act;
        area = a;
    }
    
    public AreaEvent(String act, Area a, int x, int y){
        action = act;
        area = a;
        this.x = x;
        this.y = y;
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
    
}
