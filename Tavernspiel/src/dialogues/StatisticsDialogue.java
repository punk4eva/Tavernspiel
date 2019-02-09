
package dialogues;

import creatures.Hero;
import gui.mainToolbox.Screen;

/**
 *
 * @author Adam Whittaker
 * 
 * This class opens the statistics info pop-up for the Hero.
 */
public class StatisticsDialogue extends Dialogue{
    
    private final Hero hero;
    
    public StatisticsDialogue(Hero player){
        super("Statistics", true, new String[]{});
        hero = player;
    }

    @Override
    public void screenClicked(Screen.ScreenEvent name){
    }
    
}
