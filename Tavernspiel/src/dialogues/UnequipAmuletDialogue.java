
package dialogues;

import gui.MainClass;
import items.Apparatus;
import items.Item;

/**
 *
 * @author Adam Whittaker
 * 
 * A Dialogue to unequip Amulets.
 */
public class UnequipAmuletDialogue extends Dialogue{
    
    private final String opA, opB;
    
    /**
     * Creates a new instance.
     * @param am0 The first amulet.
     * @param am1 The second amulet.
     */
    public UnequipAmuletDialogue(Item am0, Item am1){
        super("You can only wear two misc. items at a time, which do you want to"
                + "unequip?", (String) null, ((Apparatus) am0).toString(4), 
                ((Apparatus) am1).toString(4));
        opA = ((Apparatus) am0).toString(4);
        opB = ((Apparatus) am1).toString(4);
    }
    
    /**
     * Returns which item to unequip.
     * @param game The MainClass to act upon.
     * @return The index of the unequipped Item.
     */
    public int next(MainClass game){
        String ret = super.action(game).getName();
        if(ret==null) return -1;
        if(ret.equals(opA)) return 0;
        return 1;
    }
    
}
