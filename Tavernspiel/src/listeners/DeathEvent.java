
package listeners;

import creatures.Creature;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class DeathEvent{
    
    private final Creature creature;
    private final int x;
    private final int y;
    private final Area area;
    
    public DeathEvent(Creature c, int x, int y, Area a){
        creature = c;
        this.x = x;
        area = a;
        this.y = y;
    }
    
    public Creature getCreature(){
        return creature;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void notifyEvent(){
        area.lifeTaken(this);
    }
    
    public Area getArea(){
        return area;
    }
    
}
