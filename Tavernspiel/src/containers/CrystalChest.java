
package containers;

import items.Item;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Location;

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
     * @param locName
     * @param item The item within.
     * @param x
     * @param y
     */
    public CrystalChest(String locName, Item item, int x, int y){
        super((Serializable & Supplier<ImageIcon>)
                () -> Location.getCrystalChestIcon(locName), 
                "You can see " + addPronoun(item.getClass().getName()) + " in "
                + "the chest but you need a key to open the chest.", item, x, y);
    }
    
    private static String addPronoun(String str){
        if(str.charAt(0)=='y'||str.charAt(0)=='a'||str.charAt(0)=='e'||
                str.charAt(0)=='o'||str.charAt(0)=='i'||str.charAt(0)=='u')
            return "an " + str;
        return "a " + str;
    }
    
}
