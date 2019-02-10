
package dialogues.assets;

import buffs.Buff;
import creatures.Hero;
import dialogues.TextDialogue;
import gui.mainToolbox.Screen;

/**
 *
 * @author Adam Whittaker
 * 
 * This class controls the info pop-up screen that activates when the player
 * clicks on a Buff.
 */
public class BuffDialogue extends TextDialogue{
    
    private final Buff buff;
    
    public BuffDialogue(Buff b, Hero hero){
        super(true, b.icon, b.name, b.description.getDescription(hero.expertise));
        buff = b;
    }

    @Override
    public void screenClicked(Screen.ScreenEvent sc){
    }
    
}
