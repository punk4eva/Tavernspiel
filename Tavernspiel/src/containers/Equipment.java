
package containers;

import dialogues.UnequipAmuletDialogue;
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
        for(int n=0;n<7;n++) items.add(null);
    }
    
    public Equipment(ArrayList<Apparatus> i){
        super(7, i, "ERROR: You shouldn't be reading this.", -1, -1);
        while(items.size() < 7) items.add(null);
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
    
    @Override
    public String toFileString(){
        String ret = "{" + ID + "," + description + "," + x + "," + y +"|";
        return items.stream().map((item) -> item.toFileString()).reduce(ret, String::concat) + "}";
    }
    
    public static <T extends Receptacle> T getFromFileString(String filestring){
        throw new UnsupportedOperationException("Not finished.");
    }
    
    public int strengthDifference(int strength){
        int ret = 0;
        for(int n=3;n<7;n++){
            if(items.get(n)!=null) ret += (strength - ((Apparatus) items.get(n)).strength);
        }
        return ret;
    }
    
    public Apparatus equip(Apparatus app, int... choiceOfAmulet){
        if(app instanceof HeldWeapon){
            HeldWeapon ret = (HeldWeapon) items.remove(0);
            items.add(0, app);
            return ret;
        }else if(app instanceof Helmet){
            Helmet ret = (Helmet) items.remove(3);
            items.add(3, app);
            return ret;
        }else if(app instanceof Chestplate){
            Chestplate ret = (Chestplate) items.remove(4);
            items.add(4, app);
            return ret;
        }else if(app instanceof Leggings){
            Leggings ret = (Leggings) items.remove(5);
            items.add(5, app);
            return ret;
        }else if(app instanceof Boots){
            Boots ret = (Boots) items.remove(6);
            items.add(6, app);
            return ret;
        }else if(items.get(1)==null){
            items.add(1, app);
            return null;
        }else if(items.get(2)==null){
            items.add(2, app);
            return null;
        }
        int choice;
        if(choiceOfAmulet.length==0)
            choice = 1 + UnequipAmuletDialogue.next(items.get(1), items.get(2));
        else choice = choiceOfAmulet[0];
        Apparatus ret = (Apparatus) items.remove(choice);
        items.add(choice, app);
        return ret;
    }
    
}
