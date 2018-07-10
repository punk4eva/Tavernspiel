
package creatureLogic;

import ai.AITemplate;
import java.io.Serializable;

/**
 * 
 * @author Adam Whittaker
 * 
 * The base attributes that every creature has.
 */
public class Attributes implements Serializable{
    
    private final static long serialVersionUID = -1895856777;
    
    public AITemplate ai;
    public double speed = 1;
    public double attackSpeed = 1;
    public double dexterity = 1;
    public double accuracy = 1;
    public double regen = 0.025;
    public int maxhp;
    public int hp;
    public double attackMult = 1.0;
    protected Resistance[] resistances;
    public int strength = 10;
    public Level level;
    public int xpOnDeath = 0;
    public boolean invisible = false;
    
    /**
     * Creates a new instance.
     */
    public Attributes(){
        hp = 20;
        maxhp = 20;
    }
    
    /**
     * Creates a new instance.
     * @param ait The AITemplate.
     * @param sp The speed.
     * @param atksp The attack speed.
     * @param dex The dexterity.
     * @param acc The accuracy.
     * @param reg The regeneration speed.
     * @param mhp The maximum hp.
     * @param stg The strength.
     * @param xp The xp obtained by the killer on death.
     * @param rst The array of Resistances.
     */
    public Attributes(AITemplate ait, double sp, double atksp, double dex, double acc, double reg, int mhp, int stg, int xp, Resistance... rst){
        speed = sp;
        ai = ait;
        resistances = rst;
        maxhp = mhp;
        attackSpeed = atksp;
        dexterity = dex;
        accuracy = acc;
        regen = reg;
        hp = mhp;
        strength = stg;
        xpOnDeath = xp;
    }
    
    /**
     * Updates attributes.
     * @param atksp The new attack speed.
     * @param dex The new dexterity.
     * @param regen The new regeneration.
     * @param mxhp The new maximum hp.
     * @param st The new strength.
     * @param sp The new speed.
     */
    public void update(double atksp, double dex, double regen, int mxhp, int st, double sp){
        attackSpeed = atksp;
        dexterity = dex;
        this.regen = regen;
        maxhp = mxhp;
        strength = st;
        speed = sp;
    }
    
    /**
     * sets the hp value.
     * @param h The new hp.
     */
    public void setHp(int h){
        hp = h;
    }
    
}
