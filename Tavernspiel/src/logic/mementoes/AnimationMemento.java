
package logic.mementoes;

import java.awt.Dimension;
import java.io.Serializable;
import javax.swing.ImageIcon;
import level.Location;

/**
 *
 * @author Adam Whittaker
 * 
 * Stores the Icon states for an Animation.
 */
public class AnimationMemento implements Serializable{
    
    private static final long serialVersionUID = 532692198;
    
    private final IconPointer[] pointers;
    private final String loc;
    
    /**
     * Creates an instance.
     * @param icons The pointers
     * @param l The Location
     */
    public AnimationMemento(Dimension[] icons, Location l){
        if(l!=null){
            loc = l.name;
            pointers = new IconPointer[icons.length];
            for(int n=0;n<icons.length;n++)
                pointers[n] = new IconPointer(icons[n]);
        }else{
            loc = null;
            pointers = null;
        }
    }
    
    /**
     * Retrieves the ImageIcon array.
     * @return
     */
    public final ImageIcon[] getIcons(){
        if(pointers==null) return null;
        Location location = Location.locationMap.get(loc);
        ImageIcon ret[] = new ImageIcon[pointers.length];
        for(int n=0;n<pointers.length;n++)
            ret[n] = pointers[n].getIcon(location);
        return ret;
    }
    
}
