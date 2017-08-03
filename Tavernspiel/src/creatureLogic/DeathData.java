
package creatureLogic;

import creatures.Hero;
import items.ItemBuilder;
import items.consumables.Potion;
import items.consumables.Scroll;
import items.equipment.Ring;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class DeathData{
    
    public Hero hero;
    public int maximumDepth = 1;
    public int mobsKilled = 0;
    public int goldCollected = 0;
    public int foodEaten = 0;
    public int potionsCooked = 0;
    public int pirhanasKilled = 0;
    public int timesResurrected = 0;
    public int bossesSlain = 0;
    public ArrayList<Badge> badges = new ArrayList<>();
    public ArrayList<Potion> potionsIdentified = new ArrayList<>();
    public ArrayList<Scroll> scrollsIdentified = new ArrayList<>();
    public ArrayList<Ring> ringsIdentified = new ArrayList<>();
    public ArrayList<Potion> potionsToIdentify = ItemBuilder.getListOfAllPotions();
    public ArrayList<Scroll> scrollsToIdentify = ItemBuilder.getListOfAllScrolls();
    public ArrayList<Ring> ringsToIdentify = ItemBuilder.getListOfAllRings();
    
    public boolean monsterSlainWithAGrimWeapon = false;
    public int mobsKilledThisDepth = 0;
    public int mobsKilledThisStage = 0;
    public boolean movingToNewDepth = false;
    public boolean movingToNewStage = false;
    public boolean onSpecialDepth = false;
    
    public DeathData(Hero h){
        hero = h;
    }
    
}
