
package creatures;

import animation.Animation;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attributes;
import gui.MainClass;
import java.awt.Graphics;
import java.util.ArrayList;
import listeners.BuffEvent;
import listeners.BuffListener;
import listeners.DeathEvent;
import logic.Fileable;
import logic.GameObject;

/**
 * 
 * @author Adam Whittaker
 * 
 * Base Creature that all others inherit from.
 */
public class Creature extends GameObject implements BuffListener, Fileable{
    
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
        MainClass.buffinitiator.addBuffListener(this);
    }
    
    public Creature(String n, String desc, Attributes atb, Animation an, int ac){
        super(n, desc, an, ac);
        attributes = atb;
        MainClass.buffinitiator.addBuffListener(this);
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

    @Override
    public void buffTriggered(BuffEvent be){
        if(be.getID()==ID){
            switch(be.getName()){
                //@unfinished
                default: buffs.add(be.getNext());
            }
        }
    }
    
    public void decrementAndUpdateBuffs(){
        buffs.stream().forEach((buff) -> {
            buff.duration--;
            if(buff.duration==0){
                buffs.remove(buff);
                buff.end();
            }
        });
    }

    @Override
    public void turn(){
        //@unfinished
        decrementAndUpdateBuffs();   
    }

    @Override
    public void render(Graphics g){
        //@unfinished
    }

    @Override
    public String toFileString(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T getFromFileString(String filestring){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
