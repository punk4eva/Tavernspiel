
package listeners;

import buffs.Buff;
import creatures.Creature;
import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 */
public class BuffEvent implements Serializable{
    
    private final static long serialVersionUID = 2036043239;
    
    private Buff followingBuff = null; //null if none.
    private final Creature creature;
    private final String name;
    
    public BuffEvent(String n, Creature c){
        name = n;
        creature = c;
    }
    
    public BuffEvent(String n, Creature c, Buff follow){
        name = n;
        creature = c;
        followingBuff = follow;
    }
    
    public String getName(){
        return name;
    }
    
    public Buff getNext(){
        return followingBuff;
    }
    
    public void fire(){
        creature.buffTriggered(this);
    }
    
}
