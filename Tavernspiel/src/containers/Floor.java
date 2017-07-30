
package containers;

import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class Floor extends Receptacle{
    
    public Floor(int x, int y){
        super("There is nothing interesting here.", x, y);
    }
    
    public Floor(Item i, int x, int y){
        super("You shouldn't be reading this.", x, y);
        push(i);
    }
    
    public Floor(Receptacle r, int x, int y) throws ReceptacleOverflowException{
        super("You shouldn't be reading this.", x, y);
        pushAll(r);
        description = items.isEmpty() ? "There is nothing interesting here." : 
                items.get(items.size()-1).description;
    }
    
    @Override
    public void push(Item i){
        try{super.push(i);}catch(ReceptacleOverflowException ignore){}
        description = i.description;
    }
    
    @Override
    public Item pop() throws ReceptacleIndexOutOfBoundsException{
        description = items.size()==1 ? "There is nothing interesting here." : 
                items.get(items.size()-2).description;
        return super.pop();
    }
    
}
