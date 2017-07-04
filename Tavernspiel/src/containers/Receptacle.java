
package containers;

import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import items.Apparatus;
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
    public int capacity = 1000;
    
    public Receptacle(){
    
    }
    
    public Receptacle(int cap){
        capacity = cap;
    }
    
    public Receptacle(ArrayList<Item> i, int cap){
        items = i;
        capacity = cap;
    }
    
    public Receptacle(int cap, ArrayList<Apparatus> i){
        capacity = cap;
        items.addAll(i);
    }
    
    public Item get(int slot) throws ReceptacleIndexOutOfBoundsException{
        if(slot<0||slot-1>capacity){
            throw new ReceptacleIndexOutOfBoundsException("Slot " + slot + " is"
                    + " out of bounds for a receptacle with size " + capacity);
        }
        return items.get(slot);
    }
    
    private void clear(){
        items = new ArrayList<>();
    }
    
    public void trimToCapacity(){
        ArrayList<Item> ret = new ArrayList<>();
        for(int n=0;n<capacity;n++) ret.add(items.get(n));
        items = ret;
    }
    
    public Item peek() throws ReceptacleIndexOutOfBoundsException{
        if(items.isEmpty()) throw new ReceptacleIndexOutOfBoundsException(
                "Receptacle is empty.");
        return items.get(0);
    }
    
    public Item pop() throws ReceptacleIndexOutOfBoundsException{
        if(items.isEmpty()) throw new ReceptacleIndexOutOfBoundsException(
                "Receptacle is empty.");
        Item ret = items.get(0);
        items.remove(ret);
        return ret;
    }
    
    public void push(Item item) throws ReceptacleOverflowException{
        if(capacity==items.size()) throw new ReceptacleOverflowException("This"
        + " Receptacle is full.");
        items.add(item);
    }
    
}
