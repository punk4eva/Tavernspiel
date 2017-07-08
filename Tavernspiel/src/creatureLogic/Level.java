
package creatureLogic;

import logic.Formula;

/**
 *
 * @author Adam Whittaker
 */
public class Level{
    
    public int level = 1;
    public int xp = 0;
    public int xpReq = 20;
    public Formula xpFormula = new Formula();
    public Formula speedFormula = new Formula();
    public Formula attackSpeedFormula = new Formula();
    public Formula dexterityFormula = new Formula();
    public Formula hpFormula = new Formula();
    public Formula regenSpeedFormula = new Formula();
    public Formula strengthFormula = new Formula();
    
    private void levelUp(Attributes atb){
        level++;
        int prevHp = atb.maxhp;
        xpReq = xpFormula.getInt(level);
        atb.attackSpeed = attackSpeedFormula.getDouble(level);
        atb.dexterity = dexterityFormula.getDouble(level);
        atb.regenSpeed = regenSpeedFormula.getDouble(xp);
        atb.maxhp = hpFormula.getInt(level);
        atb.strength = strengthFormula.getInt(level);
        atb.speed = speedFormula.getDouble(level);
        atb.hp += atb.maxhp - prevHp;
    }

    public void gainXP(int e, Attributes atb){
        xp += e;
        while(xp>=xpReq){
            levelUp(atb);
             xp -= xpReq;
        }
    }
    
}
