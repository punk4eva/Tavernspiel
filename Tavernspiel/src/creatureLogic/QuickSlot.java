
package creatureLogic;

import containers.Inventory;
import creatures.Hero;
import gui.Window;
import gui.mainToolbox.Screen.ScreenEvent;
import items.Item;
import items.Usable;
import java.io.Serializable;
import listeners.ScreenListener;

public class QuickSlot implements Serializable, ScreenListener{

    private final Item[] items = new Item[4];
    private final Inventory inventory;
    private final Hero hero;
    
    public Item getItem(int index){
        return items[index];
    }
    
    public void setIndex(int index, int val){
        items[index] = inventory.getElse(val);
    }
    
    public void clearIndex(int index){
        items[index] = null;
    }
    
    public int length(){
        return items.length;
    }
    
    public QuickSlot(Hero h, Inventory inv){
        inventory = inv;
        hero = h;
    }
    
    @Override
    public void screenClicked(ScreenEvent sc){
        try{
            int slot = Integer.parseInt(sc.getName().substring(sc.getName().length()-1));
            if(items[slot]==null){
                hero.hijackInventoryManager(this, true);
                Window.main.setInventoryActive(true);
            }else ((Usable)items[slot]).defaultUse(hero);
        }catch(NullPointerException e){}
    }
    
}
