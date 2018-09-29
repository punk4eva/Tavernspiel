
package items.equipment.weapons;

import creatureLogic.Description;
import items.equipment.MeleeWeapon;

/**
 *
 * @author Adam Whittaker
 */
public class Tanto extends MeleeWeapon{
    
    public Tanto(){
        super(2.1, "Tanto", 32, 64, new Description("weapons","A long dagger, commonly used by assassins and the military.","This weapon is durable."), 160, 13, 4, 13, 1, 1, 1.1, 0);
    }
    
}
