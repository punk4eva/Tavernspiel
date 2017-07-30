
package containers;

import items.Item;

/**
 *
 * @author Adam Whittaker
 */
public class CrystalChest extends Chest{
    
    public CrystalChest(Item item, int x, int y){
        super(item, x, y);
        description = "You can see " + item.getPronounedName(0, "an") + " in "
                + "the chest but you need a key to open the chest.";
    }
    
    public CrystalChest(Item item, int x, int y, int id){
        super(item, x, y, id);
        description = "You can see " + item.getPronounedName(0, "an") + " in "
                + "the chest but you need a key to open the chest.";
    }
    
}
