
package items.equipment;

import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class MeleeWeapon extends HeldWeapon{
    
    public final double accuracy;
    public final double speed;
    public final int reach;
    public final double damageBlock;
    
    public MeleeWeapon(String s, String desc, ImageIcon ic, int dur, Distribution d, int st, double ac, double sp, int re, double db){
        super(s, desc, ic, dur, d, st);
        accuracy = ac;
        speed = sp;
        reach = re;
        damageBlock = db;
    }
    
}
