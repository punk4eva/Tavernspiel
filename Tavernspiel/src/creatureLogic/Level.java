
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
    
    private final static long serialVersionUID = 73313124;
    
    public int level = 0;
    public int xp = 0;
    public int xpReq = 20;
    public Formula xpFormula = new Formula(5, 10);

    /**
     * Gains the given amount of experience.
     * @param e The amount of xp.
     * @param atb The attributes to level up.
     */
    public void gainXP(int e, Attributes atb){
        xp += e;
        while(xp>=xpReq){
            level++;
            xpReq = (int)xpFormula.get(level);
            xp -= xpReq;
        }
    }
    
}
