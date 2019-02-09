
package dialogues;

import gui.mainToolbox.Screen;

/**
 *
 * @author Adam Whittaker
 * 
 * This class is the variant of ItemDialogue for money.
 */
public class MoneyDialogue extends Dialogue{
    
    public MoneyDialogue(int amount){
        super("A pile of " + amount + " gold coins.\n\nGold is a "
                + "precious resource and can be used to buy items." , true, new String[]{});
    }

    @Override
    public void screenClicked(Screen.ScreenEvent name){
    }
    
}
