
package listeners;

import buffs.Buff;

/**
 *
 * @author Adam Whittaker
 */
public class BuffEvent{
    
    private Buff followingBuff = null; //null if none.
    private final int ID;
    private final String name;
    
    public BuffEvent(String n, int id){
        name = n;
        ID = id;
    }
    
    public BuffEvent(String n, int id, Buff follow){
        name = n;
        ID = id;
        followingBuff = follow;
    }
    
    public String getName(){
        return name;
    }
    
    public int getID(){
        return ID;
    }
    
    public Buff getNext(){
        return followingBuff;
    }
    
}
