
package items;

import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 * 
 * Base class which handles items.
 */
public abstract class Item{
    
    public String name;
    public ImageIcon icon;
    public int quantity = 1;
    public boolean stackable = true;
    
    public Item(String n, ImageIcon i){
        name = n;
        icon = i;
    }
    
    public Item(String n, ImageIcon i, int q){
        name = n;
        icon = i;
        quantity = q;
    }
    
    public Item(String n, ImageIcon i, boolean s){
        name = n;
        icon = i;
        stackable = s;
    }
    
}
