
package dialogues.assets;

import creatures.Hero;
import dialogues.ButtonDialogue;
import gui.mainToolbox.Screen;

/**
 *
 * @author Adam Whittaker
 * 
 * This class opens the statistics info pop-up for the Hero.
 */
public class StatisticsDialogue extends ButtonDialogue{
    
    private final Hero hero;
    
    public StatisticsDialogue(Hero player){
        super(true, null, "Statistics", "", new String[]{"Exit"});
        hero = player;
    }

    @Override
    public void screenClicked(Screen.ScreenEvent name){
        deactivate();
    }
    
}
