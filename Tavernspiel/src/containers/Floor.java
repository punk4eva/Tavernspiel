
package containers;

import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import gui.MainClass;
import items.Item;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents items on the ground.
 */
public class Floor extends Receptacle{
    
    /**
     * Creates a new receptacle.
     * @param x
     * @param y
     */
    public Floor(int x, int y){
        super("There is nothing interesting here.", x, y);
    }
    
    /**
     * Creates a new receptacle.
     * @param i The item within.
     * @param x
     * @param y
     */
    public Floor(Item i, int x, int y){
        super("You shouldn't be reading this.", x, y);
        push(i);
    }
    
    /**
     * Copies from a receptacle to a new one.
     * @param r The receptacle to copy.
     * @param x
     * @param y
     */
    public Floor(Receptacle r, int x, int y){
        super("You shouldn't be reading this.", x, y);
        try{pushAll(r);}
        catch(ReceptacleOverflowException e){
            e.printStackTrace(MainClass.exceptionStream);
        }
        description = items.isEmpty() ? "There is nothing interesting here." : 
                items.get(items.size()-1).description;
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
        super("You shouldn't be reading this.", x, y);
        items.addAll(ary);
        description = items.isEmpty() ? "There is nothing interesting here." :
        items.get(items.size()-1).description;
    }
    
    @Override
    public final void push(Item i){
        try{super.push(i);}catch(ReceptacleOverflowException ignore){}
        description = i.description;
    }
    
    @Override
    public final Item pop(){
        description = items.size()==1 ? "There is nothing interesting here." : 
                items.get(items.size()-2).description;
        return super.pop();
    }
    
}
