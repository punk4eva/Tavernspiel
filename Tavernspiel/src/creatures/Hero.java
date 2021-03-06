
package creatures;

import ai.PlayerAI;
import animation.CreatureAnimator;
import containers.HeroEquipment;
import containers.HeroInventory;
import creatureLogic.Attack;
import creatureLogic.Attributes;
import creatureLogic.DeathData;
import creatureLogic.Description;
import creatureLogic.Expertise;
import gui.Game;
import gui.Window;
import gui.mainToolbox.Main;
import static gui.mainToolbox.MouseInterpreter.MOVE_RESOLUTION;
import static gui.mainToolbox.MouseInterpreter.getCenter;
import static gui.mainToolbox.MouseInterpreter.zoom;
import gui.mainToolbox.Screen;
import java.awt.Graphics2D;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import level.Area;
import listeners.ScreenListener;
import logic.ConstantFields;
import static logic.ConstantFields.beginHeight;
import static logic.ConstantFields.beginWidth;
import static logic.ConstantFields.padding;
import static logic.ConstantFields.sqheight;
import static logic.ConstantFields.sqwidth;
import logic.ImageUtils;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents the Hero.
 */
public class Hero extends Creature{
    
    public final LinkedList<Screen> screens = new LinkedList<>();
    public int hunger = 100;
    public DeathData data;
    public Expertise expertise = new Expertise();
    //public EnClass job = EnClass.NoClass; @Unfinished might remove
    //public EnSubclass subclass = null; //Null if no subclass selected.
    
    /**
     * Creates a new Hero.
     * @param atb The attributes.
         */
    @Unfinished("Unnessesary catch")
    public Hero(Attributes atb){
        super("Hero", new Description("hero","UNWRITTEN"), atb, 
                new CreatureAnimator(ImageUtils.convertToBuffered(new ImageIcon("graphics/spritesheets/tree.png")),
                new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5}));
        inventory = new HeroInventory(this);
        attributes.ai = new PlayerAI(this);
        try{data = new DeathData(this);}catch(Exception e){}
        //scrollBuilder = new ScrollBuilder(this);
        screens.addAll(((HeroEquipment)inventory.equipment).screens);
        screens.addAll(((HeroInventory)inventory).screens);
    }
    
    @Override
    public void render(Graphics2D g, int focusX, int focusY){
        int[] c = getCenter();
        if(moving==null) animator.animate(g, x*16+focusX, y*16+focusY);
        else{
            System.out.println("AWT DETECTED MOTION");
            moving[0]++;
            if(moving[0]>=MOVE_RESOLUTION){
                System.out.println("AWT RELEASED TURN");
                ((PlayerAI)attributes.ai).release();
                animator.animate(g, c[0], c[1]);
                moving = null;
            }else{
                moving[1] += moving[5];
                moving[2] += moving[6];
                Window.main.setDirectFocus(focusX-(int)(moving[5]/zoom), focusY-(int)(moving[6]/zoom));
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
    
    /*@Override @Unfinished replace with trauma system
    public void takeDamage(Attack attack){
        attributes.hp -= attack.damage;
        if(attributes.hp<=0){
            if(inventory.contains("ankh")){
                throw new UnsupportedOperationException("Not supported yet!");
            }else die(attack);
        }
    }*/
    
    @Override
    public void turn(double delta){
        int buffNum = buffs.size();
        super.turn(delta);
        if(buffNum!=buffs.size()) Window.main.resetGUIScreens();
    }
    
    /**
     * Killed by a Creature.
     * @param attack The killer attack.
     */
    public void die(Attack attack){
        animator.switchTo("die");
        area.lifeTaken(this);
        Main.addMessage(ConstantFields.badColor, attack.name);
        ((Game)Window.main).endGame();
    }
    
    /**
     * Paints this Hero's Inventory and Equipment onto the given Graphics.
     * @param g
     */
    public void paintInventory(Graphics2D g){
        ((HeroInventory)inventory).paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding, ((HeroInventory)inventory).manager.predicate);
        ((HeroEquipment)inventory.equipment).paint(g, beginWidth, beginHeight, sqwidth, sqheight, padding);
    }

    /**
     * Returns all the Screens of the Inventory.
     * @return
     */
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

    /**
     * Hijacks the InventoryManager. This reroutes all ScreenEvents to the
     * hijacker.
     * @param hijacker
     */
    public void hijackInventoryManager(ScreenListener hijacker){
        ((HeroInventory)inventory).manager.hijacker = hijacker;
    }

    /**
     * Stops the Inventory hijack.
     */
    public void stopInventoryHijack(){
        ((HeroInventory)inventory).manager.hijacker = null;
    }
    
}
