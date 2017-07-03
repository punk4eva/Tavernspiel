
package items.equipment;

import buffs.Buff;
import items.Apparatus;
import javax.swing.ImageIcon;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class Artifact extends Apparatus{
    
    public Buff buff;
    public int charges;
    public int maxCharges;
    
    public Artifact(String n, ImageIcon i, int dur, Distribution a, Buff b, int charge){
        super(n, i, dur, a);
    }
    
}
