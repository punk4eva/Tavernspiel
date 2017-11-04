
package items.consumables;

import creatures.Creature;
import creatures.Hero;
import gui.MainClass;
import gui.Screen;
import gui.Window;
import items.Item;
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
    public void use(Creature c){
        if(c instanceof Hero){
            hero = (Hero) c;
            hero.setScreenListener(this);
            Window.main.addViewable(hero);
        }else new RuntimeException("Creature is using LocationSpecificScroll.use()").printStackTrace(MainClass.exceptionStream);
    }
    
    /**
     * A use() method specifically for an Item. 
     * @param c The reader.
     * @param i The item.
     */
    public abstract void use(Creature c, Item i);
    
    @Override
    @Catch("Exception should never be thrown if done right.")
    public void screenClicked(Screen.ScreenEvent sc){
        switch(sc.getName()){
            case "background":
                Window.main.removeTopViewable();
                break;
            case "Weapon": if(hero.equipment.getWeapon()!=null){
                use(hero, hero.equipment.getWeapon());
            } break;
            case "Helmet": if(hero.equipment.getHelmet()!=null){
                use(hero, hero.equipment.getHelmet());
            } break;
            case "Chestplate": if(hero.equipment.getChestplate()!=null){
                use(hero, hero.equipment.getChestplate());
            } break;
            case "Leggings": if(hero.equipment.getLeggings()!=null){
                use(hero, hero.equipment.getLeggings());
            } break;
            case "Boots": if(hero.equipment.getBoots()!=null){
                use(hero, hero.equipment.getBoots());
            } break;
            case "Amulet1": if(hero.equipment.getAmulet1()!=null){
                use(hero, hero.equipment.getAmulet1());
            } break;
            case "Amulet2": if(hero.equipment.getAmulet2()!=null){
                use(hero, hero.equipment.getAmulet2());
            } break;
        }
        try{
            int n = Integer.parseInt(sc.getName());
            Item item = hero.inventory.getElse(n);
            if(item!=null) use(hero, item);
        }catch(Exception e){}
    }
    
}
