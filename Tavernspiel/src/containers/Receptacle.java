
package containers;

import creatureLogic.Description;
import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import items.Apparatus;
import items.Item;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles storage of items.
 * (name Container is taken)
 * 
 * @TOPLEVEL
 */
public abstract class Receptacle implements Serializable{
    
    private final static long serialVersionUID = -1893808300;
    
    public List<Item> items = new LinkedList<>();
    public int capacity = 1000;
    public Description description;
    public int x, y; 
    
    
    public interface Sort{
        public boolean select(Item i);
    }
    
    /**
     * Creates a new Receptacle.
     * @param desc The description.
     * @param xc The x coord.
     * @param yc The y coord.
     */
    public Receptacle(String desc, int xc, int yc){
        description = Description.parseDescription("receptacle", desc);
        x = xc;
        y = yc;
    }
    
    /**
     * Creates a new Receptacle.
     * @param cap The capacity.
     * @param desc The description.
     * @param xc The x coord.
     * @param yc The y coord.
     */
    public Receptacle(int cap, String desc, int xc, int yc){
        description = Description.parseDescription("receptacle", desc);
        capacity = cap;
        x = xc;
        y = yc;
    }
    
    /**
     * Creates a new Receptacle.
     * @param i The items within.
     * @param cap The capacity.
     * @param desc The description.
     * @param xc The x coord.
     * @param yc The y coord.
     */
    public Receptacle(List<Item> i, int cap, String desc, int xc, int yc){
        description = Description.parseDescription("receptacle", desc);
        items = i;
        capacity = cap;
        x = xc;
        y = yc;
    }
    
    /**
     * Creates a new Receptacle.
     * @param cap The capacity.
     * @param i The list of apparatus.
     * @param desc The description.
     * @param xc
     * @param yc
     */
    public Receptacle(int cap, List<Apparatus> i, String desc, int xc, int yc){
        description = Description.parseDescription("receptacle", desc);
        items.addAll(i);
        capacity = cap;
        x = xc;
        y = yc;
    }
    
    /**
     * Retrieves an item from given slot.
     * @param slot The index of th item to get.
     * @return The item.
     * @throws ReceptacleIndexOutOfBoundsException if out of given index.
     */
    public Item get(int slot) throws ReceptacleIndexOutOfBoundsException{
        if(slot<0||slot>=items.size()){
            throw new ReceptacleIndexOutOfBoundsException("Slot " + slot + " is"
                    + " out of bounds for a receptacle with size " + items.size());
        }
        return items.get(slot);
    }
    
    /**
     * Retrieves an item from given slot.
     * @param slot The index of th item to get.
     * @return The item, null if the slot is empty.
     */
    public Item getElse(int slot){
        try{
            return items.get(slot);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }
    
    /**
     * Clears this receptacle.
     */
    protected void clear(){
        items = new LinkedList<>();
    }
    
    /**
     * Trims the receptacle to its capacity. 
     */
    public void trimToCapacity(){
        LinkedList<Item> ret = new LinkedList<>();
        for(int n=0;n<capacity;n++) ret.add(items.get(n));
        items = ret;
    }
    
    /**
     * Gets the zeroth element.
     * @return null if it doesn't exist.
     */
    public Item peek(){
        if(items.isEmpty()) return null;
        return items.get(items.size()-1);
    }
    
    /**
     * Removes and returns the zeroth element 
     * @return null if it doesn't exist.
     */
    public Item pop(){
        if(items.isEmpty()) return null;
        return items.remove(0);
    }
    
    /**
     * Adds an item to this receptacle.
     * @param item The item to add.
     * @throws ReceptacleOverflowException If the receptacle is full.
     */
    public void push(Item item) throws ReceptacleOverflowException{
        if(capacity==items.size()) throw new ReceptacleOverflowException("This"
                + " Receptacle is full.");
        items.add(item);
    }
    
    /**
     * Pushes the contents of the given receptacle into this one.
     * @param r The receptacle to clone.
     * @throws ReceptacleOverflowException If the receptacle will overflow.
     */
    public final void pushAll(Receptacle r) throws ReceptacleOverflowException{
        if(capacity<items.size()+r.items.size()) throw new 
                ReceptacleOverflowException("This Receptacle is full.");
        items.addAll(r.items);
    }
    
    /**
     * Checks if the receptacle is empty.
     * @return true if it is, false if not.
     */
    public boolean isEmpty(){
        return items.isEmpty();
    }
    
    /**
     * Checks if the receptacle contains the given item.
     * @param i The item to check for.
     * @return true if the names match, false if not.
     */
    public boolean contains(Item i){
        return items.stream().anyMatch(item -> (item.name.endsWith(i.name)));
    }
    
    /**
     * Checks if the receptacle contains the given name of item.
     * @param i the name of the item to check for.
     * @return true if the names match, false if not.
     */
    public boolean contains(String i){
        return items.stream().anyMatch(item -> (item.name.endsWith(i)));
    }
    
    /**
     * Keeps only items based on a given predicate. 
     * @param sort The predicate to base on
     * @return True if items were lost, false if not.
     */
    public boolean keep(Sort sort){
        int s = items.size();
        items = items.stream().filter(item -> sort.select(item)).collect(Collectors.toList());
        return items.size()!=s;
    }
    
    /**
     * Swaps the Item in this Receptacle with another.
     * @param i The other Item.
     * @return The regex.
     */
    public Item swapItem(Item i){
        if(items.size()!=1) throw new IllegalStateException();
        items.add(i);
        return items.remove(0);
    }
    
}
