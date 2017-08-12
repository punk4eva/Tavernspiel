
package buffs;

import listeners.BuffEvent;
import creatureLogic.AttributeModifier;
import creatures.Creature;
import gui.MainClass;
import logic.Distribution;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 * 
 * The Buffs that creatures can experience.
 */
public class Buff implements Fileable{
    
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
        c.removeBuff(this);
        if(event!=null){
            Buff next = event.getNext();
            if(next==null) MainClass.buffinitiator.notify(event);
            else c.addBuff(next);
        }
    }

    @Override
    public String toFileString(){
        return name + "<->" + duration + "<->" + damageDistribution.toFileString()
                + "<->" + atribMod.toFileString() + "<->" + visible + "<->" + 
                event.toFileString();
    }
    
    public static Buff getFromFileString(String filestring){
        String profile[] = filestring.split("<->");
        Buff ret = new Buff(profile[0], Double.parseDouble(profile[1]), AttributeModifier.getFromFileString(profile[3]));
        ret.damageDistribution = Distribution.getFromFileString(profile[2]);
        ret.visible = Boolean.parseBoolean(profile[4]);
        ret.event = BuffEvent.getFromFileString(profile[5]);
        return ret;
    }
    
}
