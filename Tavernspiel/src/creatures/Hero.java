
package creatures;

import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Equipment;
import containers.Inventory;
import creatureLogic.Attributes;
import creatureLogic.DeathData;
import creatureLogic.Description;
import creatureLogic.Expertise;
import gui.Game;
import gui.MainClass;
import gui.Screen;
import gui.Viewable;
import gui.Window;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import level.Area;
import listeners.DeathEvent;
import listeners.ScreenListener;

/**
 *
 * @author Adam Whittaker
 */
public class Hero extends Creature implements Viewable{
    
    public final LinkedList<Screen> screens;
    private ScreenListener currentScreenListener;
    private final MainClass main;
    public int hunger = 100;
    public DeathData data;
    public Expertise expertise;
    public EnClass job = EnClass.NoClass;
    public EnSubclass subclass = null; //Null if no subclass selected.
    
    /**
     * The class of the hero.
     */
    public enum EnClass{
        NoClass (new Expertise()),
        Warrior (new Expertise(1,0,0,2,1,0,0), new EnSubclass[]{EnSubclass.Berserker, EnSubclass.Gladiator}),
        Mage (new Expertise(0,1,2,0,0,2,1), new EnSubclass[]{EnSubclass.Battlemage, EnSubclass.Warlock}),
        Rogue (new Expertise(1,1,1,1,1,0,2), new EnSubclass[]{EnSubclass.Freerunner, EnSubclass.Assassin}),
        Huntress (new Expertise(2,1,0,0,1,0,1), new EnSubclass[]{EnSubclass.Warden, EnSubclass.Sniper});
        
        protected final EnSubclass[] possibleSubclasses;
        protected final Expertise expertiseGained;
        EnClass(Expertise e, EnSubclass... subclasses){
            expertiseGained = e;
            possibleSubclasses = subclasses;
        }
    }
    
    /**
     * The hero's subclass.
     */
    public enum EnSubclass{
        Berserker (new Expertise(1,0,0,0,0,0,0), "Not finished"), Gladiator (new Expertise(0,0,0,0,1,0,0), "Not finished"),
        Battlemage (new Expertise(0,0,0,1,1,0,0), "Not finished"), Warlock (new Expertise(1,1,0,0,0,0,1), "Not finished"),
        Freerunner (new Expertise(0,0,1,1,0,0,0), "Not finished"), Assassin (new Expertise(1,0,0,0,1,0,0), "Not finished"),
        Warden (new Expertise(0,1,1,0,0,0,0), "Not finished"), Sniper (new Expertise(0,0,0,0,1,1,1), "Not finished");
        
        protected final String description;
        protected final Expertise expertiseGained;
        EnSubclass(Expertise e, String desc){
            expertiseGained = e;
            description = desc;
        }
    }
    
    /**
     * Creates a new Hero.
     * @param atb The attributes.
     * @param an The animator.
     * @param ac The area.
     * @param m The MainClass to register with.
     */
    public Hero(Attributes atb, GameObjectAnimator an, Area ac, MainClass m){
        super("Hero", new Description("hero","UNWRITTEN"), atb, an, ac, m.getHandler());
        data = new DeathData(this);
        screens = getScreens();
        main = m;
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
     * @param ac The Area.
     * @param m The MainClass.
     */
    public Hero(int id, Equipment eq, Inventory inv, int hung, DeathData da, EnClass j, EnSubclass sub, Attributes atb, LinkedList<Buff> bs, Area ac, MainClass m){
        super("Hero", new Description("hero","UNWRITTEN"), id, eq, inv, atb, ac, bs, m.getHandler());
        hunger = hung;
        job = j;
        subclass = sub;
        main = m;
        data = da;
        screens = getScreens();
    }
    
    /**
     * Returns the MainClass associated with this Hero.
     * @return The MainClass.
     */
    public MainClass getMainClass(){
        return main;
    }

    @Override
    public void turn(double delta){
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
            }else die(c);
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
    
    @Override
    public void paint(Graphics g){
        int padding = 4;
        int beginWidth = MainClass.WIDTH/9;
        int beginHeight = MainClass.HEIGHT/9;
        int sqwidth = (MainClass.WIDTH*7/9-7*padding)/6;
        int sqheight = (MainClass.WIDTH*7/9-6*padding)/5;
        inventory.paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding);
        equipment.paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding);
    }

    @Override
    public final LinkedList<Screen> getScreens(){
        LinkedList<Screen> ret = new LinkedList<>();
        ret.addAll(inventory.screens);
        ret.addAll(equipment.screens);
        return ret;
    }
    
    @Override
    public LinkedList<Screen> getScreenList(){
        return screens;
    }
    
    /**
     * Sets the ScreenListener (used with Viewable)
     * @param sl The new ScreenListener
     */
    public void setScreenListener(ScreenListener sl){
        currentScreenListener = sl;
    }
    
    /**
     * Returns the ScreenListener
     * @return The ScreenListener
     */
    public ScreenListener getScreenListener(){
        return currentScreenListener;
    }
    
}
