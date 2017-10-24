
package items;

import creatureLogic.Description;

/**
 *
 * @author Adam Whittaker
 */
public class Gold extends Item{
    
    public Gold(int quantity){
        super("Gold", new Description("gold", "This is a pile of gold."),
                ItemBuilder.getIcon(6, 1), quantity);
    }
    
}
