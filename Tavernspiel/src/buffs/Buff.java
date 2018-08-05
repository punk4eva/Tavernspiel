
package buffs;

import creatureLogic.Description;
import creatures.Creature;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Iterator;
import javax.swing.ImageIcon;

/**
 *
 * @author Adam Whittaker
 * 
 * The Buffs that creatures can experience.
 */
public abstract class Buff implements Serializable{
    
    private final static long serialVersionUID = 2081907;
    
    public final String name;
    public final Description description;
    public double duration = 1000000;
    public boolean visible = false, markedForEnd = false;
    public transient ImageIcon icon, smallIcon;
    
    /**
     * Creates a new Buff with the given name.
     * @param n The name of the buff.
     * @param desc The Description.
     */
    public Buff(String n, Description desc){
        name = n;
        description = desc;
        icon = BuffBuilder.buffMap.get(name);
        smallIcon = BuffBuilder.buffMap.get(name + "Small");
    }
    
    /**
     * Creates a new Buff with the given name and duration.
     * @param n The name of the buff.
     * @param desc The Description.
     * @param d The duration.
     */
    public Buff(String n, Description desc, double d){
        name = n;
        description = desc;
        icon = BuffBuilder.buffMap.get(name);
        smallIcon = BuffBuilder.buffMap.get(name + "Small");
        duration = d;
    }
    
    /**
     * Creates a new Buff with the given name, duration and visibility.
     * @param n The name of the buff.
     * @param desc The Description.
     * @param d The duration.
     * @param v Whether the Buff is visible.
     */
    public Buff(String n, Description desc, double d, boolean v){
        name = n;
        description = desc;
        icon = BuffBuilder.buffMap.get(name);
        smallIcon = BuffBuilder.buffMap.get(name + "Small");
        duration = d;
        visible = v;
    }
    
    /**
     * Decrements the Buff's duration by the given amount and ends it if necessary.
     * @param delta The amount of turns.
     * @param c The victim of the buff.
     * @param iter The iterator of buffs.
     */
    public void decrement(double delta, Creature c, Iterator iter){
        if(markedForEnd){
            end(c);
            iter.remove();
        }else{
            for(;delta>=1;delta--){
                duration--;
                turn(c, iter);
                if(duration<=0){
                    end(c);
                    iter.remove();
                    return;
                }
            }
            if(delta>0){
                double frac = duration - Math.floor(duration);
                if(delta>frac) turn(c, iter);
                duration -= delta;
                if(duration<=0){
                    end(c);
                    iter.remove();
                }
            }
        }
    }
    
    /**
     * What the Buff does at the start.
     * @param c The victim
     */
    public abstract void start(Creature c);
    
    /**
     * What the Buff does each turn.
     * @param c The victim
     * @param iter The iterator of buffs.
     */
    public abstract void turn(Creature c, Iterator iter);
    
    /**
     * What the Buff does at the end.
     * @param c The victim
     * @param iter The iterator of buffs.
     */
    public abstract void end(Creature c);
    
    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException{
        in.defaultReadObject();
        icon = BuffBuilder.buffMap.get(name);
        smallIcon = BuffBuilder.buffMap.get(name + "Small");
    }
    
}
