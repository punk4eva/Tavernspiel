
package ai;

import containers.Floor;
import containers.PurchasableHeap;
import creatures.Creature;
import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import level.Area;

/**
 *
 * @author Adam Whittaker
 */
public class AIBaseActions{
    
    
    public static void move(Creature c, int directionCode){
        switch(directionCode){
            case 1: c.setXY(c.x-1, c.y-1);
            case 2: c.setY(c.y-1);
            case 3: c.setXY(c.x+1, c.y-1);
            case 4: c.setX(c.x-1);
            case 5: c.setX(c.x+1);
            case 6: c.setXY(c.x-1, c.y+1);
            case 7: c.setY(c.y-1);
            default: c.setXY(c.x+1, c.x+1);
        }
        c.moveAnimation();
    }
    
    public static void buy(Creature c, PurchasableHeap heap, Area area){
        if(c.inventory.amountOfMoney>=heap.price){
            try{
                c.inventory.push(heap.pop());
                area.replaceHeap(heap.x, heap.y, new Floor(heap.x, heap.y));
            }catch(ReceptacleOverflowException | ReceptacleIndexOutOfBoundsException e){
                area.replaceHeap(heap.x, heap.y, new Floor(heap.items.get(0), heap.x, heap.y));
            }
            c.inventory.setMoneyAmount(c.inventory.amountOfMoney-heap.price);
        }
    }
    
}
