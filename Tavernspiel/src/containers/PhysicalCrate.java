
package containers;

import creatureLogic.Description;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import listeners.Interactable;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents Receptacles that have a physical existence, i.e: not
 * just Inventory.
 */
public abstract class PhysicalCrate extends Crate implements Interactable{
    
    public Description description;
    public int x, y; 
    public transient ImageIcon icon;
    private final Supplier<ImageIcon> loader;
    
    /**
     * Creates a new Receptacle.
     * @param l The icon.
     * @param desc The description.
     * @param xc The x coord.
     * @param yc The y coord.
     */
    public PhysicalCrate(Supplier<ImageIcon> l, String desc, int xc, int yc){
        description = Description.parseDescription("receptacle", desc);
        x = xc;
        y = yc;
        loader = l;
        if(l!=null) icon = l.get();
    }
    
    /**
     * Creates a new Receptacle.
     * @param l The icon.
     * @param cap The capacity.
     * @param desc The description.
     * @param xc The x coord.
     * @param yc The y coord.
     */
    public PhysicalCrate(Supplier<ImageIcon> l, int cap, String desc, int xc, int yc){
        description = Description.parseDescription("receptacle", desc);
        capacity = cap;
        x = xc;
        y = yc;
        loader = l;
        if(l!=null) icon = l.get();
    }
    
    
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        System.err.println("loader: " + loader);
        if(loader!=null) icon = loader.get();
    }
    
}
