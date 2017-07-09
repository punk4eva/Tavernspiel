
package creatures;

import animation.Animation;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attributes;
import gui.MainClass;
import java.util.ArrayList;
import listeners.DeathEvent;
import logic.GameObject;

/**
 * 
 * @author Adam Whittaker
 * 
 * Base Creature that all others inherit from.
 */
public abstract class Creature extends GameObject{
    
    public Equipment equipment = new Equipment();
    public Inventory inventory = new Inventory();
    public Attributes attributes;
    private Animation dieAnimation;
    private int x, y;
    public ArrayList<Buff> buffs = new ArrayList<>();
    
    public Creature(String n, String desc, Equipment eq, Inventory inv, 
            Attributes atb, Animation an, int ac){
        super(n, desc, an, ac);
        equipment = eq;
        inventory = inv;
        attributes = atb;
    }
    
    public Creature(String n, String desc, Attributes atb, Animation an, int ac){
        super(n, desc, an, ac);
        attributes = atb;
    }
    
    public void gainXP(int e){
        attributes.level.gainXP(e, attributes);
    }
    
    public void gainXP(Creature c){
        attributes.level.gainXP(c.attributes.xpOnDeath, attributes);
    }
    
    public void die(){
        MainClass.reaper.notify(new DeathEvent(this, x, y, areaCode));
    }
    
    public void startDieAnimation(){
        dieAnimation.start(this);
    }
    
    @Override
    public void tick(){
        //super.tick();
        if(attributes.maxhp!=attributes.hp){
            if(attributes.hp<=0){
                if(inventory.contains("ankh")){
                    //unfinished
                }else die();
            }
        }
    }
    
    public boolean hasBuff(String buff){
        return buffs.stream().anyMatch((b) -> (b.name.equals(buff)));
    }
    
    public void removeBuff(String buff){
        for(Buff b : buffs) if(buff.equals(b.name)){
            buffs.remove(b);
            break;
        }
    }
    
    public void addBuff(Buff buff){
        buffs.add(buff);
    }
    
}
