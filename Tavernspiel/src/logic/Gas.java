
package logic;

import animation.Animation;
import buffs.Buff;

/**
 *
 * @author Adam Whittaker
 */
public class Gas extends GameObject{
    
    public Buff buff;
    public int spreadNumber;
    
    public Gas(String n, String desc, Buff b, Animation a, int spread){
        super(n, desc, a);
        buff = b;
        spreadNumber = spread;
    }
    
}
