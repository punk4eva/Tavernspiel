
package dialogues;

import buffs.Buff;
import gui.Window;

/**
 *
 * @author Adam Whittaker
 */
public class BuffDialogue extends Dialogue{
    
    private final Buff buff;
    
    public BuffDialogue(Buff b){
        super(b.name, "offClick");
        buff = b;
    }
    
    public void next(){
        action(Window.main);
    }
    
}
