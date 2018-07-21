
package items.consumables;

import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.Window;
import items.Item;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import javax.swing.ImageIcon;
import listeners.ScreenListener;
import logic.Utils.Catch;

/**
 *
 * @author Adam Whittaker
 * 
 * A Scroll that needs an Item to work on.
 */
public abstract class ItemSpecificScroll extends Scroll implements ScreenListener{
    
    private Hero hero;
    private boolean used = false;
    private transient CyclicBarrier barrier = new CyclicBarrier(2);

    /**
     * Creates a new instance.
     * @param n The name of this Item.
     * @param desc The description of this Item.
     * @param i The image of this Item.
     * @param idd Whether this Consumable is identified.
     */
    public ItemSpecificScroll(String n, String desc, ImageIcon i, boolean idd){
        super(n, desc, i, idd);
    }

    @Override
    @Catch("Exception should never be thrown if done right.")
    public boolean use(Creature c){
        if(c instanceof Hero){
            hero = (Hero) c;
            hero.hijackInventoryManager(this, false);
            Window.main.setInventoryActive(true, true);
            try{
                barrier.await();
            }catch(InterruptedException | BrokenBarrierException ex){}
        }else new RuntimeException("Creature is using ItemSpecificScroll.use()").printStackTrace(Main.exceptionStream);
        return used;
    }
    
    /**
     * A use() method specifically for an Item. 
     * @param c The reader.
     * @param i The item.
     * @return Whether the scroll has been consumed during use.
     */
    public abstract boolean use(Creature c, Item i);
    
    @Override
    @Catch("Exception should never be thrown if done right.")
    public void screenClicked(Screen.ScreenEvent sc){
        switch(sc.getName()){
            case "invspace": return;
            case "background":
                Window.main.removeViewable();
                used = false;
                break;
            case "Weapon": if(hero.equipment.weapon!=null){
                used = use(hero, hero.equipment.weapon);
            } break;
            case "Helmet": if(hero.equipment.helmet!=null){
                used = use(hero, hero.equipment.helmet);
            } break;
            case "Chestplate": if(hero.equipment.chestplate!=null){
                used = use(hero, hero.equipment.chestplate);
            } break;
            case "Leggings": if(hero.equipment.leggings!=null){
                used = use(hero, hero.equipment.leggings);
            } break;
            case "Boots": if(hero.equipment.boots!=null){
                used = use(hero, hero.equipment.boots);
            } break;
            case "Amulet1": if(hero.equipment.amulet1!=null){
                used = use(hero, hero.equipment.amulet1);
            } break;
            case "Amulet2": if(hero.equipment.amulet2!=null){
                used = use(hero, hero.equipment.amulet2);
            } break;
            default: try{
                int n = Integer.parseInt(sc.getName());
                Item item = hero.inventory.getElse(n);
                if(item!=null) used = use(hero, item);
            }catch(NumberFormatException e){}
        }
        if(used) hero.stopInventoryHijack();
        try{
            barrier.await();
        }catch(InterruptedException | BrokenBarrierException ex){}
    }
    
    
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        barrier = new CyclicBarrier(2);
    }
    
}
