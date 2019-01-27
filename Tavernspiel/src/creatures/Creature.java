
package creatures;

import animation.CreatureAnimator;
import buffs.Buff;
import containers.Inventory;
import creatureLogic.Attack;
import creatureLogic.Attack.CreatureAttack;
import creatureLogic.Attributes;
import creatureLogic.Description;
import creatureLogic.FieldOfView;
import enchantments.WeaponEnchantment;
import gui.Window;
import gui.mainToolbox.MouseInterpreter;
import static gui.mainToolbox.MouseInterpreter.MOVE_RESOLUTION;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import logic.GameObject;
import logic.Utils.Unfinished;

/**
 * 
 * @author Adam Whittaker
 * 
 * Base Creature that all others inherit from.
 * The implementation of Comparable is for determining whether an enemy is likely to win in a fight.
 */
@Unfinished("Might remove comparable implementation since it relies on AI too "
        + "much.")
public class Creature extends GameObject implements Comparable<Creature>{
    
    public Inventory inventory;
    public volatile Attributes attributes;
    public FieldOfView FOV;
    public final LinkedList<Buff> buffs = new LinkedList<>();
    public volatile double[] moving;
    public transient CyclicBarrier motionBarrier = new CyclicBarrier(2);
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param inv The inventory.
     * @param atb The attributes.
     * @param an The Animator.
     */
    public Creature(String n, Description desc, Inventory inv, 
            Attributes atb, CreatureAnimator an){
        super(n, desc, an);
        inventory = inv;
        attributes = atb;
        FOV = new FieldOfView(x, y, 6);
    }
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param atb The attributes.
     * @param an The Animator.
     */
    public Creature(String n, Description desc, Attributes atb, CreatureAnimator an){
        super(n, desc, an);
        attributes = atb;
        inventory = new Inventory(this);
        FOV = new FieldOfView(x, y, 6);
    }
    
    /**
     * Gains the given amount of experience.
     * @param e The amount.
     */
    public void gainXP(int e){
        attributes.level.gainXP(e, attributes);
        if(attributes.xpListener!=null) attributes.xpListener.xpChange(e, attributes.level.level);
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
        animator.switchFade("die");
        area.lifeTaken(this);
    }
    
    /**
     * Smoothly moves the Creature to the new coordinates.
     * @param nx
     * @param ny
     */
    public void smootheXY(int nx, int ny){
        Integer[] c = MouseInterpreter.tileToPixel(x, y), c2 = 
                MouseInterpreter.tileToPixel(nx, ny);
        double dx = (c2[0]-(double)c[0])/MOVE_RESOLUTION,
                dy = (c2[1]-(double)c[1])/MOVE_RESOLUTION;
        moving = new double[]{0, c[0], c[1], c2[0], c2[1], dx, dy, nx, ny};
    }
    
    /**
     * Handles attacks. 
     * @param attack The attack.
     */
    public void takeDamage(Attack attack){
        attributes.hp -= attack.damage;
        if(attributes.hp<=0){
            if(inventory.contains("ankh")){
                throw new UnsupportedOperationException("Not supported yet!");
            }else{
                if(attack instanceof CreatureAttack) 
                    ((CreatureAttack)attack).attacker.gainXP(attributes.xpOnDeath);
                die();
            }
        }
    }
    
    /**
     * Checks whether this Creature is currently in a smooth-move animation.
     * @return
     */
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
     * Returns the Buff with the given name if it exists and null otherwise.
     * @param b The name of the Buff.
     * @return
     */
    public Buff getBuff(String b){
        for(Buff buff : buffs) if(buff.name.equals(b)) return buff;
        return null;
    }

    /**
     * Adds the given buff.
     * @param buff The buff.
     */
    public void addBuff(Buff buff){
        synchronized(buffs){
            buffs.add(buff);
            buff.start(this);
        }
    }
    
    /**
     * Decrements each buff.
     * @param delta
     */
    public void decrementBuffs(double delta){
        synchronized(buffs){
            Buff buff;
            for(Iterator<Buff> iter = buffs.iterator();iter.hasNext();){
                buff = iter.next();
                buff.decrement(delta, this, iter);
            }
        }
    }
    
    /**
     * Focuses in on this Creature.
     */
    public void focus(){
        Window.main.setTileFocus(x, y);
    }

    @Override
    public void turn(double delta){
        for(delta+=turndelta;delta>=attributes.speed;delta-=attributes.speed){
            attributes.ai.turn(this, area);
            decrementBuffs(1.0);
        }
        turndelta = delta;
    }

    @Override
    public void render(Graphics2D g, int focusX, int focusY){
        if(moving==null) animator.animate(g, x*16+focusX, y*16+focusY);
        else{
            moving[0]++;
            if(moving[0]>=MOVE_RESOLUTION){
                try{
                    motionBarrier.await();
                }catch(InterruptedException | BrokenBarrierException e){}
                motionBarrier.reset();
                animator.animate(g, (int)moving[3], (int)moving[4]);
                moving = null;
            }else{
                moving[1] += moving[5];
                moving[2] += moving[6];
                animator.animate(g, (int)moving[1], (int)moving[2]);
            }
        }
    }
    
    /**
     * Sets the coordinates of this Creature.
     * @param nx
     * @param ny
     */
    public void setXY(int nx, int ny){
        x = nx;
        y = ny;
    }

    /**
     * Calculates the next hit damage.
     * @return The next hit damage.
     */
    public double nextHit(){
        return inventory.equipment.nextHit(attributes.strength);
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
    
    /**
     * Generates the next Attack object of this Creature.
     * @return
     */
    public Attack nextAttack(){
        int stDif = inventory.equipment.strengthDifference(attributes.strength);
        if(stDif<0) return new CreatureAttack(this, "unfinished", 
                (int)(inventory.equipment.nextHit(attributes.strength)/Math.pow(1.5, 0-stDif)), 
                attributes.accuracy*inventory.equipment.getWeaponAccuracy()/Math.pow(1.5, 0-stDif), 
                ((WeaponEnchantment)inventory.equipment.weapon.enchantment));
        return new CreatureAttack(this, "unfinished",
                inventory.equipment.nextHit(attributes.strength), 
                attributes.accuracy*inventory.equipment.getWeaponAccuracy(), 
                 ((WeaponEnchantment)inventory.equipment.weapon.enchantment));
    }
    
    
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        motionBarrier = new CyclicBarrier(2);
    }
    
}
