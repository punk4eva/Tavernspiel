
package creatureLogic;

import logic.Fileable;
import logic.Formula;

/**
 *
 * @author Adam Whittaker
 */
public class Level implements Fileable{
    
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

    @Override
    public String toFileString(){
        return level + ","+ xp + "," + xpReq + "," + xpFormula.toFileString() + 
                speedFormula.toFileString() + 
                attackSpeedFormula.toFileString() + 
                dexterityFormula.toFileString() + 
                hpFormula.toFileString() + 
                regenSpeedFormula.toFileString() + 
                strengthFormula.toFileString();
    }

    public static Level getFromFileString(String filestring){
        String[] profile = filestring.split(",");
        Level l = new Level();
        l.level = Integer.parseInt(profile[0]);
        l.xp = Integer.parseInt(profile[1]);
        l.xpReq = Integer.parseInt(profile[2]);
        l.xpFormula = Formula.getFromFileString(profile[3]);
        l.speedFormula = Formula.getFromFileString(profile[4]);
        l.attackSpeedFormula = Formula.getFromFileString(profile[5]);
        l.dexterityFormula = Formula.getFromFileString(profile[6]);
        l.hpFormula = Formula.getFromFileString(profile[7]);
        l.regenSpeedFormula = Formula.getFromFileString(profile[8]);
        l.strengthFormula = Formula.getFromFileString(profile[9]);
        return l;
    }
    
}
