
package containers;

import creatures.Creature;
import items.Apparatus;
import items.Item;
import items.misc.Gold;
import items.misc.Key;
import items.misc.Key.KeyType;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a creature's inventory.
 */
public class Inventory extends Crate{
    
    private final static long serialVersionUID = 1782937;

    public int amountOfMoney = 0;
    public final Creature owner;
    private final LinkedList<Key> keys = new LinkedList<>();
    public Equipment equipment;
    
    /**
     * Creates a new instance for a Creature.
     * @param c
     */
    public Inventory(Creature c){
        super(18);
        owner = c;
        equipment = new Equipment();
    }
    
    /**
     * Creates a new instance with a specific Equipment.
     * @param c
     * @param eq
     */
    public Inventory(Creature c, Equipment eq){
        super(18);
        owner = c;
        equipment = eq;
    }
    
    /**
     * Sets the amount of money in this Inventory.
     * @param amount
     */
    public void setMoneyAmount(int amount){
        amountOfMoney = amount;
    }
    
    /**
     * Gets an Item from the specified slot.
     * @param slot The identification string of the slot.
     * @return The Item in that slot or null if it doesn't exist.
     */
    public Item get(String slot){
        if(slot.startsWith("e")){
            switch(slot){
                case "e0": return equipment.weapon;
                case "e1": return equipment.amulet1;
                case "e2": return equipment.amulet2;
                case "e3": return equipment.helmet; 
                case "e4": return equipment.chestplate; 
                case "e5": return equipment.leggings; 
                case "e6": return equipment.boots; 
            }
        }else{
            int s = Integer.parseInt(slot);
            if(s<size()) return get(s);
        }
        return null;
    }
    
    @Override
    public boolean remove(Object i){
        if(((Item) i).stackable){
            ((Item) i).quantity--;
        }else if(!super.remove(i)){
            try{
                equipment.unequip((Apparatus)i);
                return true;
            }catch(ClassCastException e){
                return false;
            }
        }
        return false;
    }
    
    @Override
    public boolean add(Item i){
        if(i instanceof Gold) amountOfMoney += i.quantity;
        else if(i instanceof Key) keys.add((Key) i);
        else return super.add(i);
        return true;
    }
    
    /**
     * Finds and removes a key with the given type and depth.
     * @param type
     * @param depth
     * @return True if a matching key was found and removed.
     */
    public boolean pollKey(KeyType type, int depth){
        Key k;
        Iterator<Key> iter = keys.iterator();
        while(iter.hasNext()){
            k = iter.next();
            if(k.matches(type, depth)){
                iter.remove();
                return true;
            }
        }
        return false;
    }
    
}
