
package containers;

import exceptions.ReceptacleIndexOutOfBoundsException;
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
    public int capacity;
    
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
    
}
