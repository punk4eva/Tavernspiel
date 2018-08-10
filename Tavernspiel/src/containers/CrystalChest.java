
package containers;

import items.Item;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import logic.ConstantFields;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a crystal chest.
 */
public class CrystalChest extends LockedChest{
    
    private final static long serialVersionUID = 123801897210L;
    
    /**
     * Creates a new crystal chest
     * @param item The item within.
     * @param x
     * @param y
     */
    public CrystalChest(Item item, int x, int y){
        super((Serializable & Supplier<ImageIcon>)
                () -> ConstantFields.crystalChestIcon, 
                "You can see " + item.getPronounedName(0, "an") + " in "
                + "the chest but you need a key to open the chest.", item, x, y);
    }
    
}
