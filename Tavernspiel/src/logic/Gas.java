
package logic;

import animation.Animation;
import buffs.Buff;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class Gas extends GameObject{
    
    public ArrayList<Buff> buffs = new ArrayList<>();
    public int spreadNumber;
    public int duration = 10;
    
    public Gas(String n, String desc, Buff b, Animation a, int spread){
        super(n, desc, a);
        buffs.add(b);
        spreadNumber = spread;
    }
    
    public void merge(Gas gas){
        buffs.addAll(gas.buffs);
        spreadNumber = (spreadNumber + gas.spreadNumber)/2;
        duration = (int)((duration + gas.duration)/1.5);
    }
    
}
