
package logic.mementoes;

import java.io.Serializable;
import javax.swing.ImageIcon;
import level.Location;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the load instructions of an ImageIcon.
 */
public class IconPointer implements Serializable{
    
    private static final long serialVersionUID = 53267198;
    
    private final String pointer;
    
    /**
     * Creates an instance.
     * @param d
     */
    public IconPointer(String d){
        pointer = d;
    }
    
    /**
     * Retrieves the ImageIcon from this Memento.
     * @param loc The Location
     * @return
     */
    public ImageIcon getIcon(Location loc){
        return loc.getImage(pointer);
    }
    
}
