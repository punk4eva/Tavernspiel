
package creatureLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import logic.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class Badge implements Serializable{
    
    public final String name;
    public final String requirements;
    public final int id;
    public int level = 1;
    public Obtained obtainCheck;
    public boolean superBadge = false;
    
    private interface Obtained{
        boolean check(DeathData data);
    }
    
    public Badge(String n, String req, int i, int l){
        name = n;
        requirements = req;
        id = i;
        level = l;
    }
    
    public Badge(String n, String req, int i, int l, boolean superB){
        name = n;
        requirements = req;
        id = i;
        level = l;
        superBadge = superB;
    }
    
    public Badge(String n, String req, int i, int l, Obtained obcheck){
        name = n;
        requirements = req;
        id = i;
        level = l;
        obtainCheck = obcheck;
    }
    
    public Badge(String n, String req, int i, int l, Obtained obcheck, boolean superB){
        name = n;
        requirements = req;
        id = i;
        level = l;
        superBadge = superB;
        obtainCheck = obcheck;
    }
    
    public Badge(String n, String req, int i){
        name = n;
        requirements = req;
        id = i;
    }
    
    public boolean check(DeathData data){
        return obtainCheck.check(data);
    }
    
    public boolean isOverriden(Set<Badge> badges){
        return badges.stream().anyMatch((b) -> (b.id==id&&b.level>level || 
                b instanceof DeathBadge && Utils.contains(((DeathBadge) b).overridesLivingBadges, id)));
    }
    
    public static ArrayList<Badge> allLivingBadges(){
        ArrayList<Badge> badges = new ArrayList<>();
        badges.add(new Badge("Killer of rats", "10 enemies slain", 0, 1, d -> d.mobsKilled>=10));
        badges.add(new Badge("Serial Murderer", "50 enemies slain", 0, 2, d -> d.mobsKilled>=50));
        badges.add(new Badge("Professional hitman", "100 enemies slain", 0, 3, d -> d.mobsKilled>=100));
        badges.add(new Badge("Overpowered", "250 enemies slain", 0, 4, d -> d.mobsKilled>=250, true));
        badges.add(new Badge("Piggy bank", "100 gold collected", 1, 1, d -> d.goldCollected>=100));
        badges.add(new Badge("Chest of coins", "500 gold collected", 1, 2, d -> d.goldCollected>=500));
        badges.add(new Badge("Vault of coins", "2500 gold collected", 1, 3, d -> d.goldCollected>=2500));
        badges.add(new Badge("Warehouse of coins", "7500 gold collected", 1, 4, d -> d.goldCollected>=7500, true));
        badges.add(new Badge("Squire", "Level 6 reached", 2, 1, d -> d.hero.attributes.level.level>=6));
        badges.add(new Badge("Knight", "Level 12 reached", 2, 2, d -> d.hero.attributes.level.level>=12));
        badges.add(new Badge("General", "Level 18 reached", 2, 3, d -> d.hero.attributes.level.level>=18));
        badges.add(new Badge("King", "Level 24 reached", 2, 4, d -> d.hero.attributes.level.level>=24, true));
        badges.add(new Badge("Heavy drinker", "All potions identified", 3, 1, d -> d.potionsToIdentify.size()<=2));
        badges.add(new Badge("Heavy reader", "All scrolls identified", 4, 1, d -> d.scrollsToIdentify.size()<=2));
        badges.add(new Badge("Jewellry fanatic", "All rings identified", 5, 1, d -> d.ringsToIdentify.size()<=2));
        badges.add(new Badge("Exterminator", "1st boss slain", 6, 1, d -> d.bossesSlain>=1));
        badges.add(new Badge("Executioner", "2nd boss slain", 6, 2, d -> d.bossesSlain>=2));
        badges.add(new Badge("Downbringer of ", "3rd boss slain", 6, 3, d -> d.bossesSlain>=3));
        badges.add(new Badge("Kingslayer", "4th boss slain", 6, 4, d -> d.bossesSlain>=4));
        badges.add(new Badge("Budding herbologist", "3 potions cooked", 7, 1, d -> d.potionsCooked>=3));
        badges.add(new Badge("Apprentice herbologist", "6 potions cooked", 7, 2, d -> d.potionsCooked>=6));
        badges.add(new Badge("Master herbologist", "9 potions cooked", 7, 3, d -> d.potionsCooked>=9));
        badges.add(new Badge("Fullmetal alchemist", "12 potions cooked", 7, 4, d -> d.potionsCooked>=12, true));
        badges.add(new Badge("Deathbringer", "Monster slain with a grim weapon", 8, 1, d -> d.monsterSlainWithAGrimWeapon));
        badges.add(new Badge("Ninja", "Depth completed without killing any monsters", 9, 1, d -> d.mobsKilledThisDepth==0&&!d.onSpecialDepth&&d.movingToNewDepth));
        badges.add(new Badge("Ninja master", "Stage completed without killing any monsters", 9, 2, d -> d.mobsKilledThisStage==0&&d.movingToNewStage, true));
        badges.add(new Badge("Fisherman", "6 pirhanas killed", 10, 1, d -> d.pirhanasKilled>=6));
        badges.add(new Badge("Priest", "Resurrected 4 times", 12, 1, d -> d.timesResurrected>=4, true));
        badges.add(new Badge("Big stomach", "10 pieces of food eaten", 11, 1, d -> d.foodEaten>=10));
        badges.add(new Badge("Impressive stomach", "20 pieces of food eaten", 11, 2, d -> d.foodEaten>=20));
        badges.add(new Badge("Enormous stomach", "30 pieces of food eaten", 11, 3, d -> d.foodEaten>=30));
        badges.add(new Badge("Extreme stomach", "40 pieces of food eaten", 11, 4, d -> d.foodEaten>=40, true));
        return badges;
    }
    
}
