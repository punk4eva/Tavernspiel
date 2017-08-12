
package creatures;

import animation.Animation;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attributes;
import creatureLogic.DeathData;
import gui.Handler;
import gui.MainClass;
import gui.Window;
import java.awt.Graphics;
import java.util.ArrayList;
import listeners.DeathEvent;

/**
 *
 * @author Adam Whittaker
 */
public class Hero extends Creature{
    
    public int hunger = 100;
    public DeathData data;
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
    
    public Hero(Attributes atb, Animation an, int ac, Handler handler){
        super("Hero", "UNWRITTEN", atb, an, ac, handler);
        data = new DeathData(this);
    }
    
    public Hero(int id, Equipment eq, Inventory inv, int hung, DeathData da, EnClass j, EnSubclass sub, Attributes atb, ArrayList<Buff> bs, int ac, Handler handler){
        super("Hero", "UNWRITTEN", id, eq, inv, atb, ac, bs, handler);
        hunger = hung;
        job = j;
        subclass = sub;
        data = da;
    }

    @Override
    public void turn(){
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
            }else die();
        }
    }
    
    public void die(Creature killer){
        MainClass.reaper.notify(new DeathEvent(this, x, y, areaCode));
        MainClass.messageQueue.add("red", killer.name + " killed you...");
        Window.main.endGame();
    }
    
    @Override
    public String toFileString(){
        return "" + hunger + "<hero>" + data.toFileString() + "<hero>" + job.toString()
                + "<hero>" + subclass.toString() + "<hero>" + super.toFileString();
    }

    public static Hero getFromFileString(String filestring, Handler handler){
        String[] hprofile = filestring.split("<hero>");
        
        String[] profile = hprofile[4].split("<creat>");
        ArrayList<Buff> bs = new ArrayList<>();
        for(String str : profile[7].split("<-b->")) bs.add(Buff.getFromFileString(str));
        Hero ret = new Hero(Integer.parseInt(profile[2]), Equipment.getFromFileString(profile[3]), 
            Inventory.getFromFileString(profile[4]), Integer.parseInt(hprofile[0]), 
            DeathData.getFromFileString(hprofile[1]), EnClass.valueOf(hprofile[2]),
            EnSubclass.valueOf(hprofile[3]), Attributes.getFromFileString(profile[5]),
            bs, Integer.parseInt(profile[6]), handler);
        ret.x = Integer.parseInt(profile[7]);
        ret.y = Integer.parseInt(profile[8]);
        return ret;
    }
    
}
