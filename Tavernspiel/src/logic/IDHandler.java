
package logic;

/**
 *
 * @author Adam Whittaker
 */
public class IDHandler{
    
    private int id = 0;
    
    public IDHandler(){}
    
    public int genID(){
        id++;
        return id;
    }
    
}
