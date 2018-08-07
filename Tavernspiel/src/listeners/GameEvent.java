
package listeners;

/**
 *
 * @author Adam Whittaker
 * 
 * An event to an individual entity.
 * 
 * @Delete May be sub-optimal. 
 */
public class GameEvent{
    
    private final String event;
    private final int ID;
    
    public GameEvent(String ev, int id){
        event = ev;
        ID = id;
    }
    
    public String getEvent(){
        return event;
    }
    
    public int getID(){
        return ID;
    }
    
}
