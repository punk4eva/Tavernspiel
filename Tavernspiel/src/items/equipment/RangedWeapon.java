
package items.equipment;

import javax.swing.ImageIcon;
import logic.Distribution;
import logic.Formula;

/**
 *
 * @author Adam Whittaker
 */
public class RangedWeapon extends HeldWeapon{
    
    public Distribution distanceDamage;
    public Formula[] distanceDamageFormulas[];
    public Distribution distanceAccuracy;
    public Formula[] distanceAccuracyFormulas[];
    public final double speed;
    
    public RangedWeapon(String s, String desc, ImageIcon ic, int dur, Distribution d, int st, double sp){
        super(s, desc, ic, dur, d, st);
        speed = sp;
    }
    
}
