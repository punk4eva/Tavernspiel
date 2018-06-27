
package creatures;

import ai.PlayerAI;
import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
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
import gui.Viewable;
import gui.Window;
import static gui.mainToolbox.MouseInterpreter.MOVE_RESOLUTION;
import static gui.mainToolbox.MouseInterpreter.getCentre;
import items.consumables.ScrollBuilder;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import level.Area;
import listeners.DeathEvent;
import listeners.ScreenListener;
import logic.Utils.Catch;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the Hero.
 */
public class Hero extends Creature implements Viewable{
    
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
        equipment = new Equipment(this);
        attributes.ai = new PlayerAI(this);
        inventory = new Inventory(this);
        try{data = new DeathData(this);}catch(Exception e){}
        scrollBuilder = new ScrollBuilder(this);
        screens.addAll(equipment.screens);
        screens.addAll(inventory.screens);
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
    public Hero(Equipment eq, Inventory inv, int hung, DeathData da, EnClass j, EnSubclass sub, Attributes atb, LinkedList<Buff> bs){
        super("Hero", new Description("hero","UNWRITTEN"), eq, inv, atb, bs);
        hunger = hung;
        attributes.ai = new PlayerAI(this);
        job = j;
        subclass = sub;
        data = da;
        scrollBuilder = new ScrollBuilder(this);
        screens.addAll(equipment.screens);
        screens.addAll(inventory.screens);
    }

    @Override
    public void turn(double delta){
        super.turn(delta);
    }
    
    @Override
    public void render(Graphics g, int focusX, int focusY){
        int[] c = getCentre();
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
        Window.main.setTileFocus(x, y);
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
    
    public final static int padding = 8,
        beginWidth = Main.WIDTH/9,
        beginHeight = Main.HEIGHT/9,
        sqwidth = (int)(((double)Main.WIDTH*(7.0/9.0)-7*padding)/6.0),
        sqheight = (int)(((double)Main.HEIGHT*(7.0/9.0)-6*padding)/5.0);
    
    @Override
    public void paint(Graphics g){
        inventory.paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding);
        equipment.paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding);
    }

    @Override
    public final LinkedList<Screen> getScreens(){
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
    }

    public void hijackInventoryManager(ScreenListener hijacker){
        inventory.manager.hijacker = hijacker;
    }

    public void stopInventoryHijack(){
        inventory.manager.hijacker = null;
    }
    
}
