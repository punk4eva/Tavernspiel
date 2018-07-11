
package creatureLogic;

import containers.HeroInventory;
import containers.Inventory;
import creatures.Hero;
import gui.Window;
import gui.mainToolbox.Screen.ScreenEvent;
import items.Item;
import items.Usable;
import java.io.Serializable;
import listeners.ScreenListener;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the cache of items that the player can access quickly
 * without opening the inventory.
 */
public class QuickSlot implements Serializable, ScreenListener{

    private final Item[] items = new Item[4];
    private final Inventory inventory;
    private final Hero hero;
    private int slot = -1;
    
    /**
     * Creates a new instance.
     * @param h The hero
     * @param inv The inventory needs to be injected separately to the Hero
     * because the QuickSlot may be created during the initialization of the 
     * Hero.
     */
    public QuickSlot(Hero h, Inventory inv){
        inventory = inv;
        hero = h;
    }
    
    /**
     * Gets the Item from the given index.
     * @param index
     * @return The Item in the index of the QuickSlot. Could be null.
     */
    public Item getItem(int index){
        return items[index];
    }
    
    /**
     * Sets the Item in the index.
     * @param index
     * @param i The Item.
     */
    public void setIndex(int index, Item i){
        items[index] = i;
    }
    
    /**
     * Clears the Item from the index.
     * @param index
     */
    public void clearIndex(int index){
        items[index] = null;
    }
    
    /**
     * Returns the length of this QuickSlot.
     * @return
     */
    public int length(){
        return items.length;
    }
    
    @Override
    public void screenClicked(ScreenEvent sc){
        if(slot!=-1){
            String s = sc.getName();
            switch(s){
                case "background": Window.main.setInventoryActive(false, true);
                case "invspace": return;
            }
            setIndex(slot, ((HeroInventory)inventory).get(s));
        }else try{
            slot = Integer.parseInt(sc.getName().substring(sc.getName().length()-1));
            if(items[slot]==null){
                hero.hijackInventoryManager(this, true);
                Window.main.setInventoryActive(true, true, i -> i instanceof Usable);
            }else ((Usable)items[slot]).defaultUse(hero);
        }catch(NullPointerException e){}
    }
    
}
