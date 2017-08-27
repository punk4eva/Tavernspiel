
package creatures;

import animation.AnimationBuilder;
import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attributes;
import creatureLogic.Description;
import gui.Handler;
import gui.MainClass;
import items.equipment.HeldWeapon;
import java.awt.Graphics;
import java.util.ArrayList;
import level.Area;
import listeners.BuffEvent;
import listeners.BuffListener;
import listeners.DeathEvent;
import logic.Distribution;
import logic.GameObject;

/**
 * 
 * @author Adam Whittaker
 * 
 * Base Creature that all others inherit from.
 */
public class Creature extends GameObject implements BuffListener{
    
    public Equipment equipment = new Equipment();
    public Inventory inventory = new Inventory();
    public Attributes attributes;
    public ArrayList<Buff> buffs = new ArrayList<>();
    
    public Creature(String n, Description desc, Equipment eq, Inventory inv, 
            Attributes atb, GameObjectAnimator an, Area ac, Handler handler){
        super(n, desc, an, ac, handler);
        equipment = eq;
        inventory = inv;
        attributes = atb;
        MainClass.buffinitiator.addBuffListener(this);
    }
    
    public Creature(String n, Description desc, int id, Equipment eq, Inventory inv, 
            Attributes atb, Area ac, ArrayList<Buff> bs, Handler handler){
        super(n, desc, AnimationBuilder.getCreatureAnimation(n), ac, handler);
        equipment = eq;
        ID = id;
        buffs = bs;
        inventory = inv;
        attributes = atb;
        MainClass.buffinitiator.addBuffListener(this);
    }
    
    public Creature(String n, Description desc, Attributes atb, GameObjectAnimator an, Area ac, Handler handler){
        super(n, desc, an, ac, handler);
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
        new DeathEvent(this, x, y, area).notifyEvent();  
    }
    
    public void getAttacked(Creature attacker, int damage){
        attributes.hp -= damage;
        if(attributes.hp<=0){
            if(inventory.contains("ankh")){
                throw new UnsupportedOperationException("Not supported yet!");
            }else{
                attacker.gainXP(attributes.xpOnDeath);
                die();
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
    
    public void decrementAndUpdateBuffs(double delta){
        buffs.stream().forEach((buff) -> {
            buff.duration-=delta;
            if(buff.duration<=0){
                buff.end(this);
            }
        });
    }

    @Override
    public void turn(double delta){
        //@unfinished
        decrementAndUpdateBuffs(delta);   
    }

    @Override
    public void render(Graphics g){
        //@unfinished
    }
    
    public void setXY(int nx, int ny){
        x = nx;
        y = ny;
    }
    public void setX(int nx){
        x = nx;
    }
    public void setY(int ny){
        y = ny;
    }

    public void moveAnimation(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void standAnimation(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sleepAnimation(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int nextHit(){
        try{
            HeldWeapon weap = equipment.getWeapon();
            return weap.nextIntAction() + new Distribution(0, attributes.strength - weap.strength).nextInt();
        }catch(NullPointerException e){
            return new Distribution(0, attributes.strength-7).nextInt();
        }catch(IllegalArgumentException e){
            return equipment.getWeapon().nextIntAction();
        }
    }
    
    public void removeBuff(Buff b){
        buffs.remove(b);
    }
    
}
