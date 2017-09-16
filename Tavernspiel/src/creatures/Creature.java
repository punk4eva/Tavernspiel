
package creatures;

import animation.AnimationBuilder;
import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attack;
import creatureLogic.Attributes;
import creatureLogic.Description;
import gui.MainClass;
import items.equipment.HeldWeapon;
import java.awt.Graphics;
import java.util.LinkedList;
import level.Area;
import listeners.BuffEvent;
import listeners.BuffListener;
import logic.Distribution;
import logic.GameObject;

/**
 * 
 * @author Adam Whittaker
 * 
 * Base Creature that all others inherit from.
 * The implementation of Comparable is for determining whether an enemy is likely to win in a fight.
 */
public class Creature extends GameObject implements BuffListener, Comparable<Creature>{
    
    public Equipment equipment = new Equipment();
    public Inventory inventory = new Inventory();
    public Attributes attributes;
    public LinkedList<Buff> buffs = new LinkedList<>();
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param eq The equipment.
     * @param inv The inventory.
     * @param atb The attributes.
     * @param an The Animator.
     */
    public Creature(String n, Description desc, Equipment eq, Inventory inv, 
            Attributes atb, GameObjectAnimator an){
        super(n, desc, an);
        equipment = eq;
        inventory = inv;
        attributes = atb;
        MainClass.buffinitiator.addBuffListener(this);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param id The ID of the creature.
     * @param eq The equipment.
     * @param inv The inventory.
     * @param atb The attributes.
     * @param bs The list of buffs.
     */
    public Creature(String n, Description desc, int id, Equipment eq, Inventory inv, 
            Attributes atb, LinkedList<Buff> bs){
        super(n, desc, AnimationBuilder.getCreatureAnimation(n));
        equipment = eq;
        ID = id;
        buffs = bs;
        inventory = inv;
        attributes = atb;
        MainClass.buffinitiator.addBuffListener(this);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param atb The attributes.
     * @param an The Animator.
     */
    public Creature(String n, Description desc, Attributes atb, GameObjectAnimator an){
        super(n, desc, an);
        attributes = atb;
        MainClass.buffinitiator.addBuffListener(this);
    }
    
    /**
     * Gains the given amount of experience.
     * @param e The amount.
     */
    public void gainXP(int e){
        attributes.level.gainXP(e, attributes);
    }
    
    /**
     * Gains experience from killing a Creature.
     * @param c The Creature to kill.
     */
    public void gainXP(Creature c){
        attributes.level.gainXP(c.attributes.xpOnDeath, attributes);
    }
    
    /**
     * Kills this Creature.
     */
    public synchronized void die(){
        animator.switchFadeKill(this);
    }
    
    /**
     * Handles attacks. 
     * @param attack The attack.
     */
    public void getAttacked(Attack attack){
        attributes.hp -= attack.damage;
        if(attributes.hp<=0){
            if(inventory.contains("ankh")){
                throw new UnsupportedOperationException("Not supported yet!");
            }else{
                attack.attacker.gainXP(attributes.xpOnDeath);
                die();
            }
        }
    }
    
    /**
     * Checks whether the Creature has a given buff.
     * @param buff The buff to check.
     * @return True if the buff is present, false if not.
     */
    public boolean hasBuff(String buff){
        return buffs.stream().anyMatch((b) -> (b.name.equals(buff)));
    }
    
    /**
     * Removes the given buff.
     * @param buff The buff.
     */
    public void removeBuff(String buff){
        for(Buff b : buffs) if(buff.equals(b.name)){
            buffs.remove(b);
            break;
        }
    }
    
    /**
     * Adds the given buff.
     * @param buff The buff.
     */
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
    
    /**
     *
     * @param delta
     */
    public void decrementAndUpdateBuffs(double delta){
        LinkedList<Buff> temp = (LinkedList<Buff>) buffs.clone();
        temp.stream().forEach((buff) -> {
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

    /**
     * Calculates the next hit damage.
     * @return The next hit damage.
     */
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

    @Override
    public int compareTo(Creature t){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Switches the Animation. 
     * @param str The name of the animation to switch to.
     */
    public void changeAnimation(String str){
        animator.switchTo(str);
    }
    
}
