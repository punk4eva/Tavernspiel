
package items;

/**
 *
 * @author Adam Whittaker
 * 
 * Base class which handles items.
 */
public abstract class Item{
    
    public String name;
    public int quantity = 1;
    public boolean stackable = true;
    
    public Item(String n){
        name = n;
    }
    
    public Item(String n, int q){
        name = n;
        quantity = q;
    }
    
    public Item(String n, boolean s){
        name = n;
        stackable = s;
    }
    
}
