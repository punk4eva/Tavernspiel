
package buffs;

import creatureLogic.Description;
import creatures.Creature;
import java.io.Serializable;
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
    public boolean visible = false;
    public final ImageIcon icon;
    
    /**
     * Creates a new Buff with the given name.
     * @param n The name of the buff.
     * @param desc The Description.
     */
    public Buff(String n, Description desc){
        name = n;
        description = desc;
        icon = BuffBuilder.buffMap.get(name);
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
        duration = d;
        visible = v;
    }
    
    /**
     * Decrements the Buff's duration by the given amount and ends it if necessary.
     * @param delta The amount of turns.
     * @param c The victim of the buff.
     */
    public void decrement(double delta, Creature c){
        for(;delta>=1;delta--){
            duration--;
            turn(c);
            if(duration<=0){
                end(c);
                c.removeBuff(this);
                return;
            }
        }
        if(delta>0){
            double frac = duration - Math.floor(duration);
            if(delta>frac) turn(c);
            duration -= delta;
            if(duration<=0){
                end(c);
                c.removeBuff(this);
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
     */
    public abstract void turn(Creature c);
    
    /**
     * What the Buff does at the end.
     * @param c The victim
     */
    public abstract void end(Creature c);
    
}
