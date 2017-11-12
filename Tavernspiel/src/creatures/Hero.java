
package creatures;

import creatureLogic.EnClass;
import ai.PlayerAI;
import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attack;
import creatureLogic.Attributes;
import creatureLogic.DeathData;
import creatureLogic.Description;
import creatureLogic.Expertise;
import creatureLogic.EnClass.EnSubclass;
import gui.Game;
import gui.MainClass;
import gui.Screen;
import gui.Viewable;
import gui.Window;
import items.consumables.ScrollBuilder;
import java.awt.Graphics;
import java.util.LinkedList;
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
    public Expertise expertise;
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
        try{data = new DeathData(this);}catch(Exception e){}
        scrollBuilder = new ScrollBuilder(this);
        screens.addAll(inventory.screens);
        screens.addAll(equipment.screens);
    }
    
    /**
     * Creates a new Hero
     * @param id The ID.
     * @param eq The worn Equipment.
     * @param inv The Inventory.
     * @param hung The hunger.
     * @param da The DeathData.
     * @param j The Class.
     * @param sub The SubClass.
     * @param atb The Attributes.
     * @param bs The Buffs.
     */
    public Hero(int id, Equipment eq, Inventory inv, int hung, DeathData da, EnClass j, EnSubclass sub, Attributes atb, LinkedList<Buff> bs){
        super("Hero", new Description("hero","UNWRITTEN"), id, eq, inv, atb, bs);
        hunger = hung;
        attributes.ai = new PlayerAI(this);
        job = j;
        subclass = sub;
        data = da;
        scrollBuilder = new ScrollBuilder(this);
        screens.addAll(inventory.screens);
        screens.addAll(equipment.screens);
    }

    @Override
    public synchronized void turn(double delta){
        double d = delta;
        for(;d>=attributes.speed;d-=attributes.speed){
            attributes.ai.turn(this, area);
            area.turn(attributes.speed);
        }
        if(d!=0) area.turn(d);
    }
    
    public synchronized void quickTurn(){
        attributes.ai.turn(this, area);
        area.turn(attributes.speed);
    }
    
    public synchronized void turnUntilDone(){
        PlayerAI ai = (PlayerAI) attributes.ai;
        do quickTurn();
        while(ai.unfinished);
    }
    
    @Override
    public synchronized void setXY(int nx, int ny){
        x = nx;
        y = ny;
        Window.main.setFocus(x, y);
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
        MainClass.messageQueue.add("red", killer.name + " killed you...");
        ((Game)Window.main).endGame();
    }
    
    private final static int padding = 8,
        beginWidth = MainClass.WIDTH/9,
        beginHeight = MainClass.HEIGHT/9,
        sqwidth = (int)(((double)MainClass.WIDTH*(7.0/9.0)-7*padding)/6.0),
        sqheight = (int)(((double)MainClass.HEIGHT*(7.0/9.0)-6*padding)/5.0);
    
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
    public void setArea(Area a){
        area = a;
        FOV = area.overlay;
        x = a.startCoords[0];
        y = a.startCoords[1];
        FOV.update(x, y, area);
    }

    public void hijackInventoryManager(ScreenListener hijacker){
        inventory.manager.hijacker = hijacker;
    }

    public void stopInventoryHijack(){
        inventory.manager.hijacker = null;
    }
    
}
