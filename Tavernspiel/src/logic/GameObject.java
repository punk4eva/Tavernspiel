
package logic;

import animation.GameObjectAnimator;
import gui.Handler;
import gui.MainClass;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ImageIcon;
import level.Area;
import listeners.GameEvent;

/**
 *
 * @author Adam Whittaker
 * 
 * @TOPLEVEL
 */
public abstract class GameObject implements Serializable{
    
    public int ID;
    public final String name;
    public final String description;
    public final GameObjectAnimator animator;
    public int x, y;
    public Area area;
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param an The animator.
     * @param ar The area that this GameObject is in.
     * @param handler The handler to add to.
     */
    public GameObject(String n, String desc, GameObjectAnimator an, Area ar, Handler handler){
        ID = MainClass.idhandler.genID();
        name = n;
        description = desc;
        animator = an;
        area = ar;
        handler.addObject(this);
    }
    
    /**
     * What the GameObject does each turn
     * @param delta The fraction of a turn consumed.
     */
    public abstract void turn(double delta);
    /**
     * Rendering the GameObject
     * @param g The graphics to render the GameObject on.
     */
    public abstract void render(Graphics g);
    
    //Might need to make this abstract.
    public void gameEvent(GameEvent ge){
        if(ID==ge.getID()){
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    public void passEvent(GameEvent ge, Handler handler){
        handler.notify(ge);
    }
    
}
