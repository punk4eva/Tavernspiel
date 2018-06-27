
package creatures;

import animation.AnimationBuilder;
import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attack;
import creatureLogic.Attributes;
import creatureLogic.Description;
import creatureLogic.FieldOfView;
import enchantments.WeaponEnchantment;
import gui.mainToolbox.MouseInterpreter;
import static gui.mainToolbox.MouseInterpreter.MOVE_RESOLUTION;
import items.equipment.HeldWeapon;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import listeners.BuffEvent;
import logic.Distribution;
import logic.GameObject;

/**
 * 
 * @author Adam Whittaker
 * 
 * Base Creature that all others inherit from.
 * The implementation of Comparable is for determining whether an enemy is likely to win in a fight.
 */
public class Creature extends GameObject implements Comparable<Creature>{
    
    public Equipment equipment;
    public Inventory inventory = new Inventory();
    public volatile Attributes attributes;
    public FieldOfView FOV;
    public volatile LinkedList<Buff> buffs = new LinkedList<>();
    protected volatile double[] moving;
    public CountDownLatch motionLatch = new CountDownLatch(1);
    
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
        FOV = new FieldOfView(x, y, 5);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param eq The equipment.
     * @param inv The inventory.
     * @param atb The attributes.
     * @param bs The list of buffs.
     */
    public Creature(String n, Description desc, Equipment eq, Inventory inv, 
            Attributes atb, LinkedList<Buff> bs){
        super(n, desc, AnimationBuilder.getCreatureAnimation(n));
        equipment = eq;
        buffs = bs;
        inventory = inv;
        FOV = new FieldOfView(x, y, 5);
        attributes = atb;
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
        FOV = new FieldOfView(x, y, 5);
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
    public void die(){
        animator.switchFadeKill(this);
    }
    
    public void smootheXY(int nx, int ny){
        //moving = new int[]{-1, -1, (nx-x)*16, (ny-y)*16, 0, nx, ny};
        Integer[] c = MouseInterpreter.tileToPixel(x, y), c2 = 
                MouseInterpreter.tileToPixel(nx, ny);
        double dx = ((double)c2[0]-(double)c[0])/(double)MOVE_RESOLUTION,
                dy = ((double)c2[1]-(double)c[1])/(double)MOVE_RESOLUTION;
        moving = new double[]{0, c[0], c[1], c2[0], c2[1], dx, dy, nx, ny};
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
    
    public boolean animatingMotion(){
        return moving!=null;
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

    public void buffTriggered(BuffEvent be){
        switch(be.getName()){
            //@unfinished
            default: buffs.add(be.getNext());
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
        for(delta+=turndelta;delta>=attributes.speed;delta-=attributes.speed){
            attributes.ai.turn(this, area);
            decrementAndUpdateBuffs(1.0);
        }
        turndelta = delta;
    }

    @Override
    public void render(Graphics g, int focusX, int focusY){
        if(moving==null) animator.animate(g, x*16+focusX, y*16+focusY);
        else{
            moving[4]++;
            if(moving[4]>7){
                attributes.ai.BASEACTIONS.moveRaw(this, (int)moving[5], (int)moving[6]);
                moving = null;
                motionLatch.countDown();
                motionLatch = new CountDownLatch(1);
                animator.animate(g, x*16+focusX, y*16+focusY);
            }else{
                animator.animate(g, (x*16)+focusX+(int)((double)moving[4]/8.0*moving[2]),
                        (y*16)+focusY+(int)((double)moving[4]/8.0*moving[3]));
            }
        }
    }
    
    public void setXY(int nx, int ny){
        x = nx;
        y = ny;
    }

    /**
     * Calculates the next hit damage.
     * @return The next hit damage.
     */
    public int nextHit(){
        try{
            HeldWeapon weap = equipment.weapon;
            return weap.nextIntAction() + new Distribution(0, attributes.strength - weap.strength).nextInt();
        }catch(NullPointerException e){
            return new Distribution(0, attributes.strength-7).nextInt();
        }catch(IllegalArgumentException e){
            return equipment.weapon.nextIntAction();
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
    
    public Attack nextAttack(){
        int stDif = equipment.strengthDifference(attributes.strength);
        if(stDif<0) return new Attack(this, 
                (int)(equipment.nextHit(attributes.strength)/Math.pow(1.5, 0-stDif)), 
                attributes.accuracy*equipment.getWeaponAccuracy()/Math.pow(1.5, 0-stDif), 
                ((WeaponEnchantment)equipment.weapon.enchantment));
        return new Attack(this, 
                equipment.nextHit(attributes.strength), 
                attributes.accuracy*equipment.getWeaponAccuracy(), 
                 ((WeaponEnchantment)equipment.weapon.enchantment));
    }
    
}
