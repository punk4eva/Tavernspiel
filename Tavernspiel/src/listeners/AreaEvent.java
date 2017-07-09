
package listeners;

/**
 *
 * @author Adam Whittaker
 */
public class AreaEvent{
    
    private final String action;
    private int x;
    private int y;
    private final int zipcode;
    
    public AreaEvent(String act, int zip){
        action = act;
        zipcode = zip;
    }
    
    public AreaEvent(String act, int zip, int x, int y){
        action = act;
        zipcode = zip;
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
    
    public int getCode(){
        return zipcode;
    }
    
}
