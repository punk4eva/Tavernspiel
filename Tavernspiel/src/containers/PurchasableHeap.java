
package containers;

import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class PurchasableHeap extends Floor{
    
    public final int price;
    
    public PurchasableHeap(Item i, int p, int x, int y) throws ReceptacleOverflowException{
        super(i, x, y);
        capacity = 1;
        price = p;
    }
    
    public PurchasableHeap(Receptacle r, int p, int x, int y) throws ReceptacleOverflowException{
        super(r, x, y);
        price = p;
    }
    
}
