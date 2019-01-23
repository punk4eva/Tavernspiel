
package dialogues;

import creatures.Hero;
import gui.mainToolbox.Screen;

/**
 *
 * @author Adam Whittaker
 */
public class StatisticsDialogue extends Dialogue{
    
    private final Hero hero;
    
    public StatisticsDialogue(Hero player){
        super("Statistics", "offClick");
        hero = player;
    }

    @Override
    public void screenClicked(Screen.ScreenEvent name){
        checkDeactivate(name);
    }
    
}
