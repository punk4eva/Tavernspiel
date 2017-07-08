
package creatureLogic;

import ai.AI;

/**
 * 
 * @author Adam Whittaker
 * 
 * The base attributes that every creature has.
 */
public class Attributes{
    
    private AI ai;
    public double speed = 1;
    public double attackSpeed = 1;
    public double dexterity = 1;
    public double regenSpeed = 1;
    public int maxhp;
    public int hp;
    protected Resistance[] resistances;
    public int strength = 10;
    public Level level;
    public int xpOnDeath = 0;
    
}
