
package items.consumables;

import creatures.Creature;
import creatures.Hero;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.Window;
import items.Item;
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
    private CyclicBarrier barrier = new CyclicBarrier(2);

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
            hero.hijackInventoryManager(this);
            Window.main.addViewable(hero);
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
                Window.main.removeTopViewable();
                used = false;
                break;
            case "Weapon": if(hero.equipment.getWeapon()!=null){
                used = use(hero, hero.equipment.getWeapon());
            } break;
            case "Helmet": if(hero.equipment.getHelmet()!=null){
                used = use(hero, hero.equipment.getHelmet());
            } break;
            case "Chestplate": if(hero.equipment.getChestplate()!=null){
                used = use(hero, hero.equipment.getChestplate());
            } break;
            case "Leggings": if(hero.equipment.getLeggings()!=null){
                used = use(hero, hero.equipment.getLeggings());
            } break;
            case "Boots": if(hero.equipment.getBoots()!=null){
                used = use(hero, hero.equipment.getBoots());
            } break;
            case "Amulet1": if(hero.equipment.getAmulet1()!=null){
                used = use(hero, hero.equipment.getAmulet1());
            } break;
            case "Amulet2": if(hero.equipment.getAmulet2()!=null){
                used = use(hero, hero.equipment.getAmulet2());
            } break;
            default: try{
                int n = Integer.parseInt(sc.getName());
                Item item = hero.inventory.getElse(n);
                if(item!=null) used = use(hero, item);
            }catch(NumberFormatException e){}
        }
        hero.stopInventoryHijack();
        try{
            barrier.await();
        }catch(InterruptedException | BrokenBarrierException ex){}
    }
    
}
