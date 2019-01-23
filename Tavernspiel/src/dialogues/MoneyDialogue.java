
package dialogues;

import gui.mainToolbox.Screen;

/**
 *
 * @author Adam Whittaker
 */
public class MoneyDialogue extends Dialogue{
    
    public MoneyDialogue(int amount){
        super("A pile of " + amount + " gold coins.\n\nGold is a "
                + "precious resource and can be used to buy items." , "offCase");
    }

    @Override
    public void screenClicked(Screen.ScreenEvent name){
        checkDeactivate(name);
    }
    
}
