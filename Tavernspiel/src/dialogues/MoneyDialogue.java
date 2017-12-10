
package dialogues;

import gui.Window;

/**
 *
 * @author Adam Whittaker
 */
public class MoneyDialogue extends Dialogue{
    
    private static boolean online = false;
    
    public MoneyDialogue(int amount){
        super("A pile of " + amount + " gold coins.\n\nGold is a "
                + "precious resource and can be used to buy items." , "offCase");
    }
    
    public void next(){
        if(!online){
            online = true;
            action(Window.main);
            online = false;
        }
    }
    
}
