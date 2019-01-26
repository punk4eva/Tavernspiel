
package items.consumables;

import creatures.Creature;
import creatures.Hero;
import gui.Window;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import items.Item;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import listeners.ScreenListener;
import logic.Utils.Catch;

/**
 *
 * @author Adam Whittaker
 * 
 * A Scroll that needs an Item to work on.
 */
public abstract class ItemSpecificScroll extends Scroll implements ScreenListener{
    
    private final static long serialVersionUID = 4234545434732899L;
    
    private transient Hero hero;
    private boolean used = false;
    private transient CyclicBarrier barrier = new CyclicBarrier(2);

    /**
     * Creates a new instance.
     * @param n The name of this Item.
     * @param desc
     * @param sp
     */
    public ItemSpecificScroll(String n, String desc, ScrollProfile sp){
        super(n, desc, sp);
    }

    @Override
    @Catch("Exception should never be thrown if done right.")
    public boolean use(Creature c){
        if(c instanceof Hero){
            hero = (Hero) c;
            hero.hijackInventoryManager(this);
            Window.main.setInventoryActive(true);
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
                Window.main.setInventoryActive(false);
                used = false;
                break;
            case "Weapon": if(hero.inventory.equipment.weapon!=null){
                used = use(hero, hero.inventory.equipment.weapon);
            } break;
            case "Helmet": if(hero.inventory.equipment.helmet!=null){
                used = use(hero, hero.inventory.equipment.helmet);
            } break;
            case "Chestplate": if(hero.inventory.equipment.chestplate!=null){
                used = use(hero, hero.inventory.equipment.chestplate);
            } break;
            case "Leggings": if(hero.inventory.equipment.leggings!=null){
                used = use(hero, hero.inventory.equipment.leggings);
            } break;
            case "Boots": if(hero.inventory.equipment.boots!=null){
                used = use(hero, hero.inventory.equipment.boots);
            } break;
            case "Amulet1": if(hero.inventory.equipment.amulet1!=null){
                used = use(hero, hero.inventory.equipment.amulet1);
            } break;
            case "Amulet2": if(hero.inventory.equipment.amulet2!=null){
                used = use(hero, hero.inventory.equipment.amulet2);
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
