
package containers;

import creatureLogic.Description;
import items.Apparatus;
import items.Item;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles storage of items.
 * (name Container is taken)
 * 
 * @TOPLEVEL
 */
public abstract class Receptacle extends LinkedList<Item>{
    
    private final static long serialVersionUID = -1893808300;
    
    public int capacity = 1000;
    public Description description;
    public int x, y; 
    public final ImageIcon icon;
    
    /**
     * Creates a new Receptacle.
     * @param ic The icon.
     * @param desc The description.
     * @param xc The x coord.
     * @param yc The y coord.
     */
    public Receptacle(ImageIcon ic, String desc, int xc, int yc){
        description = Description.parseDescription("receptacle", desc);
        x = xc;
        y = yc;
        icon = ic;
    }
    
    /**
     * Creates a new Receptacle.
     * @param ic The icon.
     * @param cap The capacity.
     * @param desc The description.
     * @param xc The x coord.
     * @param yc The y coord.
     */
    public Receptacle(ImageIcon ic, int cap, String desc, int xc, int yc){
        description = Description.parseDescription("receptacle", desc);
        capacity = cap;
        x = xc;
        y = yc;
        icon = ic;
    }
    
    /**
     * Creates a new Receptacle.
     * @param ic The icon.
     * @param cap The capacity.
     * @param i The list of apparatus.
     * @param desc The description.
     * @param xc
     * @param yc
     */
    public Receptacle(ImageIcon ic, int cap, List<Apparatus> i, String desc, int xc, int yc){
        description = Description.parseDescription("receptacle", desc);
        addAll(i);
        capacity = cap;
        x = xc;
        y = yc;
        icon = ic;
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
    
    /*
    public void trimToCapacity(){
        for(int n=0;n<){}
    }*/
    
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
                + "larger container");
        add(i);
        return remove(0);
    }
    
}
