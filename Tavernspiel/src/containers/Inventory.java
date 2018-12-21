
package containers;

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
    private final LinkedList<Key> keys = new LinkedList<>();
    
    /**
     * Creates a new instance for a Creature.
     */
    public Inventory(){
        super(18);
    }
    
    public void setMoneyAmount(int amount){
        amountOfMoney = amount;
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
