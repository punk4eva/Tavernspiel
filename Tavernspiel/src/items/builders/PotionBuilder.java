
package items.builders;

import items.ItemMap;
import items.consumables.Potion;
import items.consumables.PotionProfile;
import items.consumables.potions.*;
import java.io.Serializable;
import java.util.HashMap;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class PotionBuilder implements Serializable{
    
    public static final HashMap<String, Boolean> idMap = new HashMap<>();
    private final HashMap<String, PotionProfile> profileMap = new HashMap<>();
    static{
        idMap.put("Flame Potion", false);
    }
    
    public static void identify(Potion p){
        idMap.put(p.name, true);
    }
    
    private PotionProfile getProfile(String name, ItemMap itMap){
        PotionProfile f = profileMap.get(name);
        if(f==null){
            f = PotionProfile.getRandomProfile(itMap.vileDist);
            profileMap.put(name, f);
        }
        return f;
    }
    
    @Unfinished()
    public Potion getRandomPotion(Distribution potDist, ItemMap itMap){
        return flamePotion(itMap);
    }
    
    
    
    public FlamePotion flamePotion(ItemMap itMap){
        return new FlamePotion(getProfile("Flame Potion", itMap));
    }
    
}
