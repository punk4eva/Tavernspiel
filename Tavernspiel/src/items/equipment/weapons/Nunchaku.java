
package items.equipment.weapons;

import creatureLogic.Description;
import items.equipment.MeleeWeapon;

/**
 *
 * @author Adam Whittaker
 */
public class Nunchaku extends MeleeWeapon{
    
    public Nunchaku(){
        super(1.3,"Nunchaku", 0 , 64, new Description("weapons","Two short wooden rods with a chain attaching them.","This weapon is rather fast and accurate. It has quite low durability."), 130, 11, 2, 8, 1, 1.2, 1.2, 0);
    }
    
}
