
package creatures;

import animation.Animation;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attributes;
import logic.GameObject;

/**
 * 
 * @author Adam Whittaker
 * 
 * Base Creature that all others inherit from.
 */
public abstract class Creature extends GameObject{
    
    private Equipment equipment = new Equipment();
    private Inventory inventory = new Inventory();
    private Attributes attributes;
    
    public Creature(String n, String desc, Equipment eq, Inventory inv, 
            Attributes atb, Animation an){
        super(n, desc, an);
        equipment = eq;
        inventory = inv;
        attributes = atb;
    }
    
    public Creature(String n, String desc, Attributes atb, Animation an){
        super(n, desc, an);
        attributes = atb;
    }
    
}
