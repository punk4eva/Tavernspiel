
package creatures;

import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attributes;
import creatureLogic.DeathData;
import gui.Handler;
import gui.MainClass;
import gui.Screen;
import gui.Viewable;
import gui.Window;
import java.awt.Graphics;
import java.util.ArrayList;
import listeners.DeathEvent;

/**
 *
 * @author Adam Whittaker
 */
public class Hero extends Creature implements Viewable{
    
    public final ArrayList<Screen> screens;
    public int hunger = 100;
    public DeathData data;
    public EnClass job = EnClass.NoClass;
    public EnSubclass subclass = null; //Null if no subclass selected.
    
    public enum EnClass{
        NoClass,
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
    
    public Hero(Attributes atb, GameObjectAnimator an, int ac, Handler handler){
        super("Hero", "UNWRITTEN", atb, an, ac, handler);
        data = new DeathData(this);
        screens = getScreens();
    }
    
    public Hero(int id, Equipment eq, Inventory inv, int hung, DeathData da, EnClass j, EnSubclass sub, Attributes atb, ArrayList<Buff> bs, int ac, Handler handler){
        super("Hero", "UNWRITTEN", id, eq, inv, atb, ac, bs, handler);
        hunger = hung;
        job = j;
        subclass = sub;
        data = da;
        screens = getScreens();
    }

    @Override
    public void turn(double delta){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(Graphics g){
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public void getAttacked(Creature c, int damage){
        attributes.hp -= damage;
        if(attributes.hp<=0){
            if(inventory.contains("ankh")){
                throw new UnsupportedOperationException("Not supported yet!");
            }else die(c);
        }
    }
    
    public void die(Creature killer){
        MainClass.reaper.notify(new DeathEvent(this, x, y, areaCode));
        MainClass.messageQueue.add("red", killer.name + " killed you...");
        Window.main.endGame();
    }
    
    @Override
    public void paint(Graphics g){
        int padding = 4;
        int beginWidth = MainClass.WIDTH/9;
        int beginHeight = MainClass.HEIGHT/9;
        int sqwidth = (MainClass.WIDTH*7/9-7*padding)/6;
        int sqheight = (MainClass.WIDTH*7/9-6*padding)/5;
        inventory.paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding, this);
        equipment.paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding, this);
    }

    @Override
    public final ArrayList<Screen> getScreens(){
        ArrayList<Screen> ret = new ArrayList<>();
        ret.addAll(inventory.screens);
        ret.addAll(equipment.screens);
        return ret;
    }
    
    @Override
    public ArrayList<Screen> getScreenList(){
        return screens;
    }
    
}
