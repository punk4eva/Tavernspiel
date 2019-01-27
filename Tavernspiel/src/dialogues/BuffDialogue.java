
package dialogues;

import buffs.Buff;
import gui.mainToolbox.Screen;

/**
 *
 * @author Adam Whittaker
 * 
 * This class controls the info pop-up screen that activates when the player
 * clicks on a Buff.
 */
public class BuffDialogue extends Dialogue{
    
    private final Buff buff;
    
    public BuffDialogue(Buff b){
        super(b.name, "offClick");
        buff = b;
    }

    @Override
    public void screenClicked(Screen.ScreenEvent sc){
        checkDeactivate(sc);
    }
    
}
