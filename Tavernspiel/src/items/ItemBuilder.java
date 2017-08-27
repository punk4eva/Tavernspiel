
package items;

import animation.Animation;
import items.consumables.Potion;
import items.consumables.Scroll;
import items.equipment.Ring;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import listeners.AreaEvent;

/**
 *
 * @author Adam Whittaker
 */
public class ItemBuilder{
    
    public static Item amulet(){
        return new Item("amulet", "Description.", new ImageIcon("graphics/amulet.png"));
    }

    public static Item get(String substring){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Animation getWandAnimation(String s){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static int getWandBlockingLevel(String s){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static AreaEvent getWandAreaEvent(String s){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static ArrayList<Ring> getListOfAllRings(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static ArrayList<Potion> getListOfAllPotions(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static ArrayList<Scroll> getListOfAllScrolls(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
