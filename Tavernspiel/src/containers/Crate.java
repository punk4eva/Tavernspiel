
package containers;

import items.Item;
import java.util.LinkedList;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles storage of items.
 */
public abstract class Crate extends LinkedList<Item>{
    
    private final static long serialVersionUID = -1893808300;
    
    public int capacity = 1000;
    
    /**
     * Creates a new instance.
     */
    public Crate(){}
    
    /**
     * Creates a new instance with the given capacity.
     * @param cap
     */
    public Crate(int cap){
        capacity = cap;
    }
    
    /**
     * Retrieves an item from given slot.
     * @param slot The index of the item to get.
     * @return The item, null if the slot is empty.
     */
    public Item getElse(int slot){
        try{
            return get(slot);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }
    
    /**
     * Adds an item to this receptacle.
     * @param item The item to add.
     * @return whether the Item was added successfully.
     */
    @Override
    public boolean add(Item item){
        if(item.stackable&&contains(item)){
            stream().filter(i -> i.name.endsWith(item.name)).forEach(i -> {
                i.quantity += item.quantity;
            });
        }else if(capacity<=size()) return false;
        else super.add(item);
        return true;
    }
    
    /**
     * Adds all the items from an Equipment object.
     * @param e The Equipment.
     */
    public void addAll(Equipment e){
        if(e.weapon     !=null) add(e.weapon    );
        if(e.helmet     !=null) add(e.helmet    );
        if(e.chestplate !=null) add(e.chestplate);
        if(e.leggings   !=null) add(e.leggings  );
        if(e.boots      !=null) add(e.boots     );
        if(e.amulet1    !=null) add(e.amulet1   );
        if(e.amulet2    !=null) add(e.amulet2   );
    }
    
    /**
     * Checks if the receptacle contains the given item.
     * @param i The item to check for.
     * @return true if the names match, false if not.
     */
    public boolean contains(Item i){
        return stream().anyMatch(item -> item.name.endsWith(i.name));
    }
    
    /**
     * Checks if the receptacle contains the given name of item.
     * @param i the name of the item to check for.
     * @return true if the names match, false if not.
     */
    public boolean contains(String i){
        return stream().anyMatch(item -> (item.name.endsWith(i)));
    }
    
    /**
     * Swaps the Item in this Receptacle with another.
     * @param i The other Item.
     * @return The regex.
     */
    public Item swapItem(Item i){
        if(size()!=1) throw new IllegalStateException("Trying to swap item of"
                + "large container");
        add(i);
        return remove(0);
    }
    
}
