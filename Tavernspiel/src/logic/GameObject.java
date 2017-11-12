
package logic;

import animation.GameObjectAnimator;
import creatureLogic.Description;
import gui.MainClass;
import java.awt.Graphics;
import java.io.Serializable;
import level.Area;
import listeners.GameEvent;

/**
 *
 * @author Adam Whittaker
 * 
 * @TOPLEVEL
 */
public abstract class GameObject implements Serializable{
    
    private static final long serialVersionUID = -1805157903;
    
    public int ID;
    public final String name;
    public final Description description;
    public final GameObjectAnimator animator;
    public volatile int x, y;
    public double turndelta = 0;
    public Area area;
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param an The animator.
     */
    public GameObject(String n, Description desc, GameObjectAnimator an){
        ID = MainClass.idhandler.genID();
        name = n;
        description = desc;
        animator = an;
    }
    
    /**
     * What the GameObject does each turn
     * @param delta The fraction of a turn consumed.
     */
    public abstract void turn(double delta);
    /**
     * Rendering the GameObject
     * @param g The graphics to render the GameObject on.
     * @param focusX The x focus.
     * @param focusY The y focus.
     * @param zoom The zoom.
     */
    public abstract void render(Graphics g, int focusX, int focusY);
    
    public void standardAnimation(Graphics g, int focusX, int focusY){
        animator.active.animate(g, x*16+focusX, y*16+focusY);
    }
    
    //Might need to make this abstract.
    public void gameEvent(GameEvent ge){
        if(ID==ge.getID()){
            throw new UnsupportedOperationException("Is this even going to be used?");
        }
    }
    
    public void passEvent(GameEvent ge){
        throw new UnsupportedOperationException("When have we ever used passEvent()?");
    }

    /**
     * Sets the GameObject's Area.
     * @param ar
     */
    public void setArea(Area ar){
        area = ar;
    }
    
}
