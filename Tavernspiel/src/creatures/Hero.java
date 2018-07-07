
package creatures;

import ai.PlayerAI;
import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Equipment;
import containers.HeroInventory;
import creatureLogic.Attack;
import creatureLogic.Attributes;
import creatureLogic.DeathData;
import creatureLogic.Description;
import creatureLogic.EnClass;
import creatureLogic.EnClass.EnSubclass;
import creatureLogic.Expertise;
import gui.Game;
import gui.mainToolbox.Main;
import gui.mainToolbox.Screen;
import gui.Window;
import static gui.mainToolbox.MouseInterpreter.MOVE_RESOLUTION;
import items.consumables.ScrollBuilder;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import level.Area;
import listeners.DeathEvent;
import listeners.ScreenListener;
import static logic.ConstantFields.beginHeight;
import static logic.ConstantFields.beginWidth;
import static logic.ConstantFields.padding;
import static logic.ConstantFields.sqheight;
import static logic.ConstantFields.sqwidth;
import logic.Utils.Catch;
import static gui.mainToolbox.MouseInterpreter.getCenter;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the Hero.
 */
public class Hero extends Creature{
    
    public final LinkedList<Screen> screens = new LinkedList<>();
    public final ScrollBuilder scrollBuilder;
    public int hunger = 100;
    public DeathData data;
    public Expertise expertise = new Expertise();
    public EnClass job = EnClass.NoClass;
    public EnSubclass subclass = null; //Null if no subclass selected.
    
    /**
     * Creates a new Hero.
     * @param atb The attributes.
     * @param an The animator.
     */
    @Catch("Unnessesary catch")
    public Hero(Attributes atb, GameObjectAnimator an){
        super("Hero", new Description("hero","UNWRITTEN"), atb, an);
        inventory = new HeroInventory(this);
        equipment = new Equipment(this);
        attributes.ai = new PlayerAI(this);
        try{data = new DeathData(this);}catch(Exception e){}
        scrollBuilder = new ScrollBuilder(this);
        screens.addAll(equipment.screens);
        screens.addAll(((HeroInventory)inventory).screens);
    }
    
    /**
     * Creates a new Hero.
     * @param eq The worn Equipment.
     * @param inv The Inventory.
     * @param hung The hunger.
     * @param da The DeathData.
     * @param j The Class.
     * @param sub The SubClass.
     * @param atb The Attributes.
     * @param bs The Buffs.
     */
    @Unfinished("May be redundant")
    public Hero(Equipment eq, HeroInventory inv, int hung, DeathData da, EnClass j, EnSubclass sub, Attributes atb, LinkedList<Buff> bs){
        super("Hero", new Description("hero","UNWRITTEN"), eq, inv, atb, bs);
        hunger = hung;
        attributes.ai = new PlayerAI(this);
        job = j;
        subclass = sub;
        data = da;
        scrollBuilder = new ScrollBuilder(this);
        screens.addAll(equipment.screens);
        screens.addAll(((HeroInventory)inventory).screens);
    }

    @Override
    public void turn(double delta){
        super.turn(delta);
    }
    
    @Override
    public void render(Graphics g, int focusX, int focusY){
        int[] c = getCenter();
        if(moving==null) animator.animate(g, x*16+focusX, y*16+focusY);
        else{
            moving[0]++;
            if(moving[0]>=MOVE_RESOLUTION){
                attributes.ai.BASEACTIONS.moveRaw(this, (int)moving[7], (int)moving[8]);
                motionLatch.countDown();
                motionLatch = new CountDownLatch(1);
                animator.animate(g, c[0], c[1]);
                moving = null;
            }else{
                moving[1] += moving[5];
                moving[2] += moving[6];
                Window.main.setDirectFocus(focusX-(int)moving[5], focusY-(int)moving[6]);
                animator.animate(g, c[0], c[1]);
            }
        }
    }
    
    @Override
    public void setXY(int nx, int ny){
        x = nx;
        y = ny;
        focus();
    }
    
    @Override
    public void getAttacked(Attack attack){
        attributes.hp -= attack.damage;
        if(attributes.hp<=0){
            if(inventory.contains("ankh")){
                throw new UnsupportedOperationException("Not supported yet!");
            }else die(attack.attacker);
        }
    }
    
    /**
     * Killed by a Creature.
     * @param killer The killer.
     */
    public void die(Creature killer){
        animator.switchTo("die");
        new DeathEvent(this, x, y, area).notifyEvent();
        Main.addMessage("red", killer.name + " killed you...");
        ((Game)Window.main).endGame();
    }
    
    public void paintInventory(Graphics g){
        ((HeroInventory)inventory).paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding, ((HeroInventory)inventory).manager.predicate);
        equipment.paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding);
    }

    public final LinkedList<Screen> getInventoryScreens(){
        return screens;
    }
    
    @Override
    public void setArea(Area a, boolean start){
        area = a;
        FOV = area.overlay;
        if(start){
            x = a.startCoords[0];
            y = a.startCoords[1];
        }else{
            x = a.endCoords[0];
            y = a.endCoords[1];
        }
        FOV.update(x, y, area);
        focus();
    }

    public void hijackInventoryManager(ScreenListener hijacker, boolean exitable){
        ((HeroInventory)inventory).manager.hijacker = hijacker;
        ((HeroInventory)inventory).manager.exitable = exitable;
    }

    public void stopInventoryHijack(){
        ((HeroInventory)inventory).manager.hijacker = null;
    }
    
}
