
package logic.mementoes;

import java.awt.Dimension;
import java.io.Serializable;
import javax.swing.ImageIcon;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the load instructions of an ImageIcon.
 */
public class IconPointer implements Serializable{
    
    private static final long serialVersionUID = 53267198;
    
    private Dimension dim;
    
    /**
     * Creates an instance.
     * @param d
     */
    public IconPointer(Dimension d){
        dim = d;
    }
    
    /**
     * Retrieves the ImageIcon from this Memento.
     * @param loc The Location
     * @return
     */
    public ImageIcon getIcon(Location loc){
        return ImageHandler.getImage(dim, loc);
    }
    
}
