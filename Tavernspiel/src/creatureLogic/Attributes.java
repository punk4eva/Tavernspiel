
package creatureLogic;

import ai.AITemplate;
import java.io.Serializable;
import listeners.XPListener;
import logic.Distribution.NormalProb;

/**
 * 
 * @author Adam Whittaker
 * 
 * The base attributes that every creature has.
 */
public class Attributes implements Serializable{
    
    private final static long serialVersionUID = -1895856777;
    
    public AITemplate ai;
    protected Resistance[] resistances; //@Unfinished might remove
    public double strength = 10;
    public WellBeing health;
    public Level level;
    public int xpOnDeath = 0;
    public boolean invisible = false;
    public XPListener xpListener;
    
    /**
     * Creates a new instance.
     */
    public Attributes(){
        health = new WellBeing();
    }
    
    /**
     * Creates a new instance.
     * @param ait The AITemplate.
     * @param sp The speed.
     * @param atksp The attack speed.
     * @param acc The accuracy.
     * @param reg The regeneration speed.
     * @param str The strength.
     * @param ev The evasion curve.
     * @param xp The xp obtained by the killer on death.
     * @param att The attack curve.
     * @param tra The trauma curve.
     * @param rst The array of Resistances.
     */
    public Attributes(AITemplate ait, double reg, double str, double sp, double atksp, int xp, NormalProb ev, NormalProb acc, NormalProb att, NormalProb tra, Resistance... rst){
        ai = ait;
        resistances = rst;
        strength = str;
        xpOnDeath = xp;
        health = new WellBeing(reg, sp, sp, atksp, ev, acc, att, tra);
    }
    
    /**
     * Updates attributes.
     * @param atksp The new attack speed.
     * @param dex The new dexterity.
     * @param regen The new regeneration.
     * @param mxhp The new maximum hp.
     * @param st The new strength.
     * @param sp The new speed.
     * 
     * @Unfinished May remove
     */
    /*public void update(double atksp, double dex, double regen, int mxhp, double st, double sp){
        attackSpeed = atksp;
        dexterity = dex;
        this.regen = regen;
        maxhp = mxhp;
        strength = st;
        speed = sp;
    }*/
    
}
