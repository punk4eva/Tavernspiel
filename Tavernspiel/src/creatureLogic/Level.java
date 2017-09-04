
package creatureLogic;

import java.io.Serializable;
import logic.Formula;

/**
 *
 * @author Adam Whittaker
 * 
 * Represents the experience of the hero.
 */
public class Level implements Serializable{
    
    public int level = 1;
    public int xp = 0;
    public int xpReq = 20;
    public Formula xpFormula;
    public Formula speedFormula;
    public Formula attackSpeedFormula;
    public Formula dexterityFormula;
    public Formula hpFormula;
    public Formula regenSpeedFormula;
    public Formula strengthFormula;
    
    private void levelUp(Attributes atb){
        level++;
        int prevHp = atb.maxhp;
        xpReq = xpFormula.getInt(level);
        atb.update(
            attackSpeedFormula.getDouble(level),
            dexterityFormula.getDouble(level),
            regenSpeedFormula.getDouble(level),
            hpFormula.getInt(level),
            strengthFormula.getInt(level),
            speedFormula.getDouble(level));
        atb.setHp(atb.maxhp - prevHp);
    }

    /**
     * Gains the given amount of experience.
     * @param e The amount of xp.
     * @param atb The attributes to level up.
     */
    public void gainXP(int e, Attributes atb){
        xp += e;
        while(xp>=xpReq){
            levelUp(atb);
             xp -= xpReq;
        }
    }
    
}
