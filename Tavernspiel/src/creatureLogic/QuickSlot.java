
package creatureLogic;

import containers.Inventory;
import creatures.Hero;
import gui.Screen.ScreenEvent;
import items.Item;
import items.Usable;
import java.io.Serializable;
import listeners.ScreenListener;

public class QuickSlot implements Serializable, ScreenListener{

    private Item[] items = new Item[4];
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
    
    public QuickSlot(Hero h, Inventory inv){
        inventory = inv;
        hero = h;
    }
    
    @Override
    public void screenClicked(ScreenEvent sc){
        try{
            ((Usable)items[Integer.parseInt(sc.getName())]).defaultUse(hero);
        }catch(NullPointerException e){}
    }
    
}
