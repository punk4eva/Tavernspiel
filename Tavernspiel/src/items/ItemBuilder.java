
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

    public static Item get(String substring){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
