
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a stash of Items that can be purchased.
 */
public class PurchasableHeap extends Floor{
    
    private final static long serialVersionUID = 23721674337L;
    
    public final int price;
    
    /**
     * Creates an instance.
     * @param i
     * @param p The price.
     * @param x
     * @param y
     */
    public PurchasableHeap(Item i, int p, int x, int y){
        super(i, x, y);
        capacity = 1;
        price = p;
    }
    
    /**
     * Creates an instance.
     * @param r
     * @param p
     */
    public PurchasableHeap(PhysicalCrate r, int p){
        super(r, r.x, r.y);
        price = p;
    }
    
}
