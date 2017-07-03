
package logic;

import animation.Animation;
import buffs.Buff;

/**
 *
 * @author Adam Whittaker
 */
public class Gas{
    
    public String name;
    public Buff buff;
    public Animation animation;
    public int spreadNumber;
    
    public Gas(String n, Buff b, Animation a, int spread){
        name = n;
        buff = b;
        animation = a;
        spreadNumber = spread;
    }
    
}
