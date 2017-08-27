
package creatures;

import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attributes;
import creatureLogic.DeathData;
import creatureLogic.Expertise;
import gui.Handler;
import gui.MainClass;
import gui.Screen;
import gui.Viewable;
import gui.Window;
import java.awt.Graphics;
import java.util.ArrayList;
import level.Area;
import listeners.DeathEvent;

/**
 *
 * @author Adam Whittaker
 */
public class Hero extends Creature implements Viewable{
    
    public final ArrayList<Screen> screens;
    public int hunger = 100;
    public DeathData data;
    public Expertise expertise;
    public EnClass job = EnClass.NoClass;
    public EnSubclass subclass = null; //Null if no subclass selected.
    
    public enum EnClass{
        NoClass (new Expertise()),
        Warrior (new Expertise(1,0,0,2,1,0), new EnSubclass[]{EnSubclass.Berserker, EnSubclass.Gladiator}),
        Mage (new Expertise(0,1,2,0,0,2), new EnSubclass[]{EnSubclass.Battlemage, EnSubclass.Warlock}),
        Rogue (new Expertise(1,1,1,1,1,0), new EnSubclass[]{EnSubclass.Freerunner, EnSubclass.Assassin}),
        Huntress (new Expertise(2,1,0,0,1,0), new EnSubclass[]{EnSubclass.Warden, EnSubclass.Sniper});
        
        private final EnSubclass[] possibleSubclasses;
        private final Expertise expertiseGained;
        EnClass(Expertise e, EnSubclass... subclasses){
            expertiseGained = e;
            possibleSubclasses = subclasses;
        }
    }
    
    public enum EnSubclass{
        Berserker (new Expertise(1,0,0,0,0,0), "Not finished"), Gladiator (new Expertise(0,0,0,0,1,0), "Not finished"),
        Battlemage (new Expertise(0,0,0,1,1,0), "Not finished"), Warlock (new Expertise(1,1,0,0,0,0), "Not finished"),
        Freerunner (new Expertise(0,0,1,1,0,0), "Not finished"), Assassin (new Expertise(1,0,0,0,1,0), "Not finished"),
        Warden (new Expertise(0,1,1,0,0,0), "Not finished"), Sniper (new Expertise(0,0,0,0,1,1), "Not finished");
        
        private final String description;
        private final Expertise expertiseGained;
        EnSubclass(Expertise e, String desc){
            expertiseGained = e;
            description = desc;
        }
    }
    
    public Hero(Attributes atb, GameObjectAnimator an, Area ac, Handler handler){
        super("Hero", "UNWRITTEN", atb, an, ac, handler);
        data = new DeathData(this);
        screens = getScreens();
    }
    
    public Hero(int id, Equipment eq, Inventory inv, int hung, DeathData da, EnClass j, EnSubclass sub, Attributes atb, ArrayList<Buff> bs, Area ac, Handler handler){
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
        new DeathEvent(this, x, y, area).notifyEvent();
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
