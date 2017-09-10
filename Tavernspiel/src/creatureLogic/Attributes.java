
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
    public double regenSpeed = 1;
    public int maxhp;
    public int hp;
    protected Resistance[] resistances;
    public int strength = 10;
    public Level level;
    public int xpOnDeath = 0;
    
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
        regenSpeed = regen;
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
