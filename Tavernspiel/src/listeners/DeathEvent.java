
package listeners;

import creatures.Creature;

/**
 *
 * @author Adam Whittaker
 */
public class DeathEvent{
    
    private final Creature creature;
    private final int x;
    private final int y;
    private final int zipcode;
    
    public DeathEvent(Creature c, int x, int y, int zip){
        creature = c;
        this.x = x;
        this.y = y;
        zipcode = zip;
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
    
    public int getCode(){
        return zipcode;
    }
    
}
