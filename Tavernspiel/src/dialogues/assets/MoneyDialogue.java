
package dialogues.assets;

import dialogues.TextDialogue;
import items.misc.Gold;

/**
 *
 * @author Adam Whittaker
 * 
 * This class is the variant of ItemDialogue for money.
 */
public class MoneyDialogue extends TextDialogue{
    
    public MoneyDialogue(int amount){
        super(Gold.getIcon(amount), "Gold", "A pile of " + amount + " gold coins.\n\nGold is a "
                + "precious resource and can be used to buy items.");
    }
    
}
