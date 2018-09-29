
package items.equipment.weapons;

import creatureLogic.Description;
import items.equipment.MeleeWeapon;

/**
 *
 * @author Adam Whittaker
 */
public class Kama extends MeleeWeapon{
    
    public Kama(){
        super(1.5, "Kama", 16, 64, new Description("weapons","A short sickle designed as a reliable, easy-to-use battle weapon.","This weapon is slightly slow and blocks slight amounts of damage."), 150, 12, 3, 9, 1, 1, 1, 1);
    }
    
}
