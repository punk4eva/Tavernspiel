
package containers;

import items.Apparatus;
import items.equipment.Helmet;
import items.equipment.Boots;
import items.equipment.Chestplate;
import items.equipment.HeldWeapon;
import items.equipment.Leggings;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class Equipment extends Receptacle{
    
    
    
    public Equipment(){
        super(7, "ERROR: You shouldn't be reading this.", -1, -1);
    }
    
    public Equipment(ArrayList<Apparatus> i){
        super(7, i, "ERROR: You shouldn't be reading this.", -1, -1);
    }
    
    public HeldWeapon getWeapon(){
        return (HeldWeapon) items.get(0);
    }
    
    public Apparatus getAmulet1(){
        return (Apparatus) items.get(1);
    }
    
    public Apparatus getAmulet2(){
        return (Apparatus) items.get(2);
    }
    
    public Helmet getHelmet(){
        return (Helmet) items.get(3);
    }
    
    public Chestplate getChestplate(){
        return (Chestplate) items.get(4);
    }
    
    public Leggings getLeggings(){
        return (Leggings) items.get(5);
    }
    
    public Boots getBoots(){
        return (Boots) items.get(6);
    }
    
    public <T> T get(T ignore, int index){
        return (T) items.get(index);
    }
    
}
