
package containers;

import java.util.ArrayList;
import items.Item;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles storage of items.
 * (name Container is taken)
 */
public abstract class Receptacle{
    
    public ArrayList<Item> items = new ArrayList<>();
    public int capacity;
    
    public Receptacle(int cap){
        capacity = cap;
    }
    
    public Receptacle(ArrayList<Item> i, int cap){
        items = i;
        capacity = cap;
    }
    
}
