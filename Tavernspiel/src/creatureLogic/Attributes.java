
package creatureLogic;

import ai.AITemplate;
import ai.IntelligentAI0;
import logic.Fileable;

/**
 * 
 * @author Adam Whittaker
 * 
 * The base attributes that every creature has.
 */
public class Attributes implements Fileable{
    
    private AITemplate ai;
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

    @Override
    public String toFileString(){
        String ret = ai.toFileString() + "," + speed + "," + attackSpeed + "," + 
                dexterity + "," + accuracy + "," + regenSpeed + "," + hp + "," + maxhp + "," + 
                strength + "," + xpOnDeath + ";";
        for(Resistance r : resistances) ret += r.toFileString() + ",";
        return ";" + ret.substring(ret.length()-1) + level.toFileString();
    }

    @Override
    public Attributes getFromFileString(String filestring){
        String profile[] = filestring.split(";");
        String AIN[] = profile[0].split(",");
        String resistances[] = profile[1].split(",");
        Attributes atrib = new Attributes();
        atrib.ai = new IntelligentAI0().getFromFileString(AIN[0]);
        atrib.speed = Integer.parseInt(AIN[1]);
        atrib.attackSpeed = Integer.parseInt(AIN[2]);
        atrib.dexterity = Integer.parseInt(AIN[3]);
        atrib.accuracy = Integer.parseInt(AIN[4]);
        atrib.regenSpeed = Integer.parseInt(AIN[5]);
        atrib.hp = Integer.parseInt(AIN[6]);
        atrib.maxhp = Integer.parseInt(AIN[7]);
        atrib.strength = Integer.parseInt(AIN[8]);
        atrib.xpOnDeath = Integer.parseInt(AIN[9]);
        atrib.resistances = new Resistance[resistances.length];
        Resistance dummy = new Resistance();
        for(int n=0;n<resistances.length;n++){
            atrib.resistances[n] = dummy.getFromFileString(resistances[n]);
        }
        atrib.level = new Level().getFromFileString(profile[2]);
        return atrib;
    }
    
}
