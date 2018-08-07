
package containers;

import creatureLogic.Description;
import items.Item;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 */
public class LockedChest extends Chest{
    
    private final static long serialVersionUID = 285389597;
    
    public LockedChest(Item item, int x, int y){
        super((Serializable & Supplier<ImageIcon>)() -> ConstantFields.lockedChestIcon, item, x, y);
        description = new Description("receptacle","You can't open this chest without a key.");
    }
    
}
