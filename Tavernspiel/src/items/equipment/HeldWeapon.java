
package items.equipment;

import items.Apparatus;
import items.builders.ItemBuilder;
import java.io.Serializable;
import java.util.function.Supplier;
import javax.swing.ImageIcon;
import level.Location.WeaponEntry;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a weapon that is equippable.
 */
public class HeldWeapon extends Apparatus{
    
    private final static long serialVersionUID = 1231141312459L;
    
    protected int value;
    
    public HeldWeapon(String s, String desc, Supplier<ImageIcon> lo, int dur, Distribution d, int st){
        super(s, desc, lo, dur, d, st);
    }
    
    public HeldWeapon(WeaponEntry entry){
        super(entry.name, entry.description, 
                (Serializable & Supplier<ImageIcon>)() -> 
                        ItemBuilder.getIcon(entry.x, entry.y), 
                entry.durability, entry.distrib, entry.strength);
        value = entry.ID;
    }
    
}
