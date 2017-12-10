
package dialogues;

import creatures.Hero;
import gui.Window;

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
    
    public void next(){
        action(Window.main);
    }
    
}
