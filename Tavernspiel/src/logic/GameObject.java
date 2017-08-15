
package logic;

import animation.Animation;
import animation.GameObjectAnimator;
import gui.Handler;
import gui.MainClass;
import java.awt.Graphics;
import listeners.GameEvent;

/**
 *
 * @author Adam Whittaker
 * 
 * @TOPLEVEL
 */
public abstract class GameObject{
    
    public int ID;
    public String name;
    public String description;
    public GameObjectAnimator animator;
    public int areaCode;
    
    public GameObject(String n, String desc, GameObjectAnimator an, int ac, Handler handler){
        ID = MainClass.idhandler.genID();
        name = n;
        description = desc;
        animator = an;
        areaCode = ac;
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
