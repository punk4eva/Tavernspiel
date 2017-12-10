
package containers;

import creatureLogic.Description;
import items.Item;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a crystal chest.
 */
public class CrystalChest extends Chest{
    
    /**
     * Creates a new crystal chest
     * @param item The item within.
     * @param x
     * @param y
     */
    public CrystalChest(Item item, int x, int y){
        super(ConstantFields.crystalChestIcon, item, x, y);
        description = new Description("receptacle", "You can see " + item.getPronounedName(0, "an") + " in "
                + "the chest but you need a key to open the chest.");
    }
    
    /*public CrystalChest(Item item, int x, int y, int id){
    super(item, x, y, id);
    description = "You can see " + item.getPronounedName(0, "an") + " in "
    + "the chest but you need a key to open the chest.";
    }*/
    
}
