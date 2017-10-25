
package items.equipment;

import items.Apparatus;
import items.ItemBuilder;
import javax.swing.ImageIcon;
import level.Location.WeaponEntry;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a weapon that is equipable.
 */
public class HeldWeapon extends Apparatus{
    
    public HeldWeapon(String s, String desc, ImageIcon ic, int dur, Distribution d, int st){
        super(s, desc, ic, dur, d, st);
        description.type = "weapons";
    }
    
    public HeldWeapon(WeaponEntry entry){
        super(entry.name, entry.description, 
                ItemBuilder.getIcon(entry.x, entry.y), 
                entry.durability, entry.distrib, entry.strength);
        description.type = "weapons";
    }
    
}
