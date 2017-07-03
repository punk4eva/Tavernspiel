
package creatures;

import animation.Animation;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attributes;

/**
 * 
 * @author Adam Whittaker
 * 
 * Base Creature that all others inherit from.
 */
public abstract class Creature{
    
    private Equipment equipment = new Equipment();
    private Inventory inventory = new Inventory();
    private Attributes attributes;
    private Animation animation;
    
    public Creature(Equipment eq, Inventory inv, Attributes atb, Animation an){
        equipment = eq;
        inventory = inv;
        attributes = atb;
        animation = an;
    }
    
    public Creature(Attributes atb, Animation an){
        attributes = atb;
        animation = an;
    }
    
}
