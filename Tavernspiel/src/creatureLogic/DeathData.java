
package creatureLogic;

import creatures.Hero;
import items.builders.ItemBuilder;
import items.consumables.Potion;
import items.consumables.Scroll;
import items.equipment.Ring;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Adam Whittaker
 * 
 * The data that is stored in the hero which is carried to his death.
 */
public class DeathData implements Serializable{
    
    private final static long serialVersionUID = 890027038;
    
    public final Hero hero;
    public int maximumDepth = 1;
    public int mobsKilled = 0;
    public int goldCollected = 0;
    public int foodEaten = 0;
    public int potionsCooked = 0;
    public int pirhanasKilled = 0;
    public int timesResurrected = 0;
    public int bossesSlain = 0;
    public List<Badge> badges = new LinkedList<>();
    public List<Potion> potionsIdentified = new LinkedList<>();
    public List<Scroll> scrollsIdentified = new LinkedList<>();
    public List<Ring> ringsIdentified = new LinkedList<>();
    public List<Potion> potionsToIdentify = ItemBuilder.getListOfAllPotions();
    public List<Scroll> scrollsToIdentify = ItemBuilder.getListOfAllScrolls();
    public List<Ring> ringsToIdentify = ItemBuilder.getListOfAllRings();
    
    public boolean monsterSlainWithAGrimWeapon = false;
    public int mobsKilledThisDepth = 0;
    public int mobsKilledThisStage = 0;
    public boolean movingToNewDepth = false;
    public boolean movingToNewStage = false;
    public boolean onSpecialDepth = false;
    
    /**
     * Creates a new instance.
     * @param h The hero.
     */
    public DeathData(Hero h){
        hero = h;
    }
    
}
