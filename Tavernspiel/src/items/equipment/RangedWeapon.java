
package items.equipment;

import creatures.Creature;
import items.ItemAction;
import items.Usable;
import javax.swing.ImageIcon;
import logic.Distribution;
import logic.Formula;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Ranged Weapon.
 */
public class RangedWeapon extends HeldWeapon implements Usable{
    
    public Distribution distanceDamage;
    public Formula[] distanceDamageFormulas[];
    public Distribution distanceAccuracy;
    public Formula[] distanceAccuracyFormulas[];
    public final double speed;
    
    /**
     * Creates a new instance.
     * @param s The name.
     * @param desc The description.
     * @param ic The image.
     * @param dur The durability.
     * @param d The action distribution.
     * @param st The strength.
     */
    public RangedWeapon(String s, String desc, ImageIcon ic, int dur, Distribution d, int st, double sp){
        super(s, desc, ic, dur, d, st);
        speed = sp;
    }

    @Override
    public boolean defaultUse(Creature c, Object... data){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean use(Creature c, ItemAction act, Object... data){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
