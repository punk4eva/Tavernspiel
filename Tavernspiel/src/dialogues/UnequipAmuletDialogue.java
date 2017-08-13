
package dialogues;

import gui.MainClass;
import items.Apparatus;
import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class UnequipAmuletDialogue extends Dialogue{
    
    private final String opA, opB;
    
    public UnequipAmuletDialogue(Item am0, Item am1){
        super("You can only wear two misc. items at a time, which do you want to"
                + "unequip?", null, ((Apparatus) am0).toString(4), 
                ((Apparatus) am1).toString(4));
        opA = ((Apparatus) am0).toString(4);
        opB = ((Apparatus) am1).toString(4);
    }
    
    public int next(MainClass game){
        String ret = super.action(game);
        if(ret==null) return -1;
        if(ret.equals(opA)) return 0;
        return 1;
    }
    
}
