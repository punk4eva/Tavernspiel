
package dialogues;

import items.Apparatus;
import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class UnequipAmuletDialogue extends Dialogue{
    
    public UnequipAmuletDialogue(Item am0, Item am1){
        super("You can only wear two misc. items at a time, which do you want to"
                + "unequip?", ((Apparatus) am0).toString(4), 
                ((Apparatus) am1).toString(4));
    }
    
    public static int next(Item am0, Item am1){
        throw new UnsupportedOperationException("Not supported yet!");
        //return 0 if they selected am0, 1 otherwise. 
    }
    
}
