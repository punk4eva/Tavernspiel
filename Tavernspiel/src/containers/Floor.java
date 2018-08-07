
package containers;

import creatureLogic.Description;
import items.Item;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents items on the ground.
 */
public class Floor extends Receptacle{
    
    private final static long serialVersionUID = 2890391007L;
    
    /**
     * Creates a new receptacle.
     * @param x
     * @param y
     */
    public Floor(int x, int y){
        super(null, "There is nothing interesting here.", x, y);
    }
    
    /**
     * Creates a new receptacle.
     * @param i The item within.
     * @param x
     * @param y
     */
    public Floor(Item i, int x, int y){
        super(null, "You shouldn't be reading this.", x, y);
        push(i);
    }
    
    /**
     * Copies from a receptacle to a new one.
     * @param r The receptacle to copy.
     * @param x
     * @param y
     */
    public Floor(Receptacle r, int x, int y){
        super(null, "You shouldn't be reading this.", x, y);
        addAll(r);
        description = isEmpty() ? new Description("tile", "There is nothing interesting here.") : 
                get(size()-1).description;
    }
    
    /*public Floor(ArrayList<Item> ary, int x, int y, int id){
    super("You shouldn't be reading this.", x, y, id);
    items.addAll(ary);
    description = items.isEmpty() ? "There is nothing interesting here." :
    items.get(items.size()-1).description;
    }*/

    
    
    /**
     * Creates an instance with given items.
     * @param ary The items within.
     * @param x
     * @param y
     */
    public Floor(ArrayList<Item> ary, int x, int y){
        super(null, "You shouldn't be reading this.", x, y);
        addAll(ary);
        description = isEmpty() ? new Description("tile", "There is nothing interesting here.") :
        get(size()-1).description;
    }
    
    @Override
    public final boolean add(Item i){
        super.add(i);
        description = i.description;
        return true;
    }
    
    @Override
    public final Item pop(){
        description = size()==1 ? new Description("tile", "There is nothing interesting here.") : 
                get(size()-2).description;
        return super.pop();
    }
    
}
