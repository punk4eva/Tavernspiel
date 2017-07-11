
package items;

import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 */
public class ItemBuilder{
    
    public static Item amulet(){
        return new Item("amulet", new ImageIcon("graphics/amulet.png"));
    }
    
}
