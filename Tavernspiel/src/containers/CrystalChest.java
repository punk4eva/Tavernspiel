
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class CrystalChest extends Chest{
    
    public CrystalChest(Item item){
        super(item);
        description = "You can see " + item.getPronounedName(0, "an") + " in "
                + "the chest but you need a key to open the chest.";
    }
    
}
