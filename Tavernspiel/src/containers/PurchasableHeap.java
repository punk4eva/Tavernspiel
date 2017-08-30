
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class PurchasableHeap extends Floor{
    
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
    
    public PurchasableHeap(Receptacle r, int p){
        super(r, r.x, r.y);
        price = p;
    }
    
}
