
package listeners;

import buffs.Buff;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 */
public class BuffEvent implements Fileable{
    
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

    @Override
    public String toFileString(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static BuffEvent getFromFileString(String filestring){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
