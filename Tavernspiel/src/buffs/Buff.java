
package buffs;

import listeners.BuffEvent;
import creatureLogic.AttributeModifier;
import creatures.Creature;
import gui.MainClass;
import java.io.Serializable;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 * 
 * The Buffs that creatures can experience.
 */
public class Buff implements Serializable{
    
    private final static long serialVersionUID = 2081907;
    
    public final String name;
    public double duration = 1000000;
    public Distribution damageDistribution = null; //null if deals no damage.
    public AttributeModifier atribMod = null; //null if no attributes modified.
    public boolean visible = false;
    private BuffEvent event = null; //The event to broadcast once the buff ends (null if no event).
    
    /**
     * Creates a new Buff with the given name.
     * @param n The name of the buff.
     */
    public Buff(String n){
        name = n;
    }
    
    /**
     * Creates a new Buff with the given name and duration.
     * @param n The name of the buff.
     * @param d The duration.
     */
    public Buff(String n, double d){
        name = n;
        duration = d;
    }
    
    /**
     * Creates a new Buff with the given name and attribute modifier.
     * @param n The name of the buff.
     * @param am The attribute modifier.
     */
    public Buff(String n, AttributeModifier am){
        name = n;
        atribMod = am;
    }
    
    /**
     * Creates a new Buff with the given name, duration, and attribute modifier.
     * @param n The name of the buff.
     * @param d The duration.
     * @param am The attribute modifier.
     */
    public Buff(String n, double d, AttributeModifier am){
        name = n;
        duration = d;
        atribMod = am;
    }
    
    /**
     * Ends the buff. Broadcasts the event (if exists) and removes itself from
     * the creature's buff list.
     * @param c The creature whose buff has ended.
     */
    public void end(Creature c){
        c.removeBuff(name);
        if(event!=null){
            Buff next = event.getNext();
            if(next==null) MainClass.buffinitiator.notify(event);
            else c.addBuff(next);
        }
    }
    
    /**
     * Decrements the Buff's duration by the given amount and ends it if necessary.
     * @param delta The amount of turns.
     * @param c The victim of the buff.
     */
    public void decrement(double delta, Creature c){
        duration -= delta;
        if(duration<=0) end(c);
    }
    
}
