
package containers;

import creatureLogic.Description;
import creatures.Creature;
import items.Item;
import java.util.ArrayList;
import java.util.ListIterator;
import level.Area;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents items on the ground.
 */
public class FloorCrate extends PhysicalCrate{
    
    private final static long serialVersionUID = 2890391007L;
    
    /**
     * Creates a new receptacle.
     * @param x
     * @param y
     */
    public FloorCrate(int x, int y){
        super(null, "There is nothing interesting here.", x, y);
    }
    
    /**
     * Creates a new receptacle.
     * @param i The item within.
     * @param x
     * @param y
     */
    public FloorCrate(Item i, int x, int y){
        super(null, "You shouldn't be reading this.", x, y);
        push(i);
    }
    
    /**
     * Copies from a receptacle to a new one.
     * @param r The receptacle to copy.
     * @param x
     * @param y
     */
    public FloorCrate(Crate r, int x, int y){
        super(null, "You shouldn't be reading this.", x, y);
        addAll(r);
        description = isEmpty() ? new Description("tile", "There is nothing interesting here.") : 
                get(size()-1).description;
    }

    
    
    /**
     * Creates an instance with given items.
     * @param ary The items within.
     * @param x
     * @param y
     */
    public FloorCrate(ArrayList<Item> ary, int x, int y){
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
    
    /**
     * Removes and returns an Item from the Floor and removes the Receptacle
     * from the Area if necessary.
     * @param area The Area.
     * @return The item at the top of the Receptacle.
     * @throws NullPointerException if there is no Receptacle.
     */
    public final Item pickUp(Area area){
        Item ret = pop();
        if(isEmpty()) area.removeReceptacle(this);
        return ret;
    }

    @Override
    public void interact(Creature c, Area a){
        c.attributes.ai.BASEACTIONS.pickUp(c, this);
    }

    @Override
    public double interactTurns(){
        return 1;
    }

    /**
     * Burns all flammable Items in this receptacle.
     * @return If this Crate is empty and needs to be removed.
     */
    public boolean burn(){
        for(ListIterator<Item> iter = listIterator();iter.hasNext();)
            if(iter.next().flammable) iter.remove();
        return isEmpty();
    }
    
}
