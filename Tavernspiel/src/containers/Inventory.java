
package containers;

import items.misc.Gold;
import items.Item;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a creature's inventory.
 */
public class Inventory extends Receptacle{
    
    private final static long serialVersionUID = 1782937;

    public int amountOfMoney = 0;
    
    /**
     * Creates a new instance for a Creature.
     */
    public Inventory(){
        super(null, 18, "ERROR: You shouldn't be reading this.", -1, -1);
    }
    
    public void setMoneyAmount(int amount){
        amountOfMoney = amount;
    }
    
    @Override
    public boolean add(Item i){
        if(i instanceof Gold) amountOfMoney += i.quantity;
        else return super.add(i);
        return true;
    }
    
}
