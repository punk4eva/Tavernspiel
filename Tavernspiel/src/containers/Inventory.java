
package containers;

import creatures.Creature;
import items.Apparatus;
import items.misc.Gold;
import items.Item;
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
public class Inventory extends Receptacle{
    
    private final static long serialVersionUID = 1782937;

    public int amountOfMoney = 0;
    public final Creature owner;
    private final LinkedList<Key> keys = new LinkedList<>();
    
    /**
     * Creates a new instance for a Creature.
     * @param c
     */
    public Inventory(Creature c){
        super(18);
        owner = c;
    }
    
    public void setMoneyAmount(int amount){
        amountOfMoney = amount;
    }
    
    @Override
    public boolean remove(Object i){
        if(!super.remove(i)){
            try{
                owner.equipment.unequip((Apparatus)i);
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
