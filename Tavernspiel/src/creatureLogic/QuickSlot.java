
package creatureLogic;

import containers.Inventory;
import creatures.Hero;
import dialogues.ExceptionDialogue;
import exceptions.ReceptacleIndexOutOfBoundsException;
import gui.Screen.ScreenEvent;
import items.Usable;
import java.awt.Graphics;
import java.io.Serializable;
import listeners.ScreenListener;

public class QuickSlot implements Serializable, ScreenListener{

    private int[] pointers;
    private final Inventory inventory;
    private final Hero hero;
    
    public void paint(Graphics g){
        
    }
    
    public QuickSlot(Hero h){
        inventory = h.inventory;
        hero = h;
    }
    
    @Override
    public void screenClicked(ScreenEvent sc){
        try{
            ((Usable)inventory.get(pointers[Integer.parseInt(sc.getName())])).defaultUse(hero);
        }catch(ReceptacleIndexOutOfBoundsException e){
            new ExceptionDialogue(e).next();
        }
    }
    
}
