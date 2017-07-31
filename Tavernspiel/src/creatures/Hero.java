
package creatures;

import animation.Animation;
import creatureLogic.Attributes;
import gui.MainClass;
import gui.Window;
import java.awt.Graphics;
import listeners.DeathEvent;

/**
 *
 * @author Adam Whittaker
 */
public class Hero extends Creature{
    
    public int hunger = 100;
    public EnClass job;
    public EnSubclass subclass = null; //Null if no subclass selected.
    
    public enum EnClass{
        Warrior (new EnSubclass[]{EnSubclass.Berserker, EnSubclass.Gladiator}),
        Mage (new EnSubclass[]{EnSubclass.Battlemage, EnSubclass.Warlock}),
        Rogue (new EnSubclass[]{EnSubclass.Freerunner, EnSubclass.Assassin}),
        Huntress (new EnSubclass[]{EnSubclass.Warden, EnSubclass.Sniper});
        
        private final EnSubclass[] possibleSubclasses;
        EnClass(EnSubclass... subclasses){
            possibleSubclasses = subclasses;
        }
    }
    
    public enum EnSubclass{
        Berserker ("Not finished"), Gladiator ("Not finished"),
        Battlemage ("Not finished"), Warlock ("Not finished"),
        Freerunner ("Not finished"), Assassin ("Not finished"),
        Warden ("Not finished"), Sniper ("Not finished");
        
        private final String description;
        EnSubclass(String desc){
            description = desc;
        }
    }
    
    public Hero(Attributes atb, Animation an, int ac){
        super("Hero", "UNWRITTEN", atb, an, ac);
    }

    @Override
    public void turn(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(Graphics g){
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public void getAttacked(Creature c, int damage){
        attributes.hp -= damage;
        if(attributes.hp<=0){
            if(inventory.contains("ankh")){
                throw new UnsupportedOperationException("Not supported yet!");
            }else die();
        }
    }
    
    public void die(Creature killer){
        MainClass.reaper.notify(new DeathEvent(this, x, y, areaCode));
        MainClass.messageQueue.add("red", killer.name + " killed you...");
        Window.main.endGame();
    }
    
    
}
