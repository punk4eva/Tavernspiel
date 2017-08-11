
package logic;

import animation.Animation;
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
    public Animation animation;
    public int areaCode;
    
    public GameObject(String n, String desc, Animation an, int ac, Handler handler){
        ID = MainClass.idhandler.genID();
        name = n;
        description = desc;
        animation = an;
        areaCode = ac;
        handler.addObject(this);
    }
    /*
    * What the Gameobject does each turn
    */
    public abstract void turn();
    /*
    * What the GameObject does each tick
    */
    public abstract void tick();
    /*
    * Rendering the GameObject
    */
    public abstract void render(Graphics g);
    
    
    public void gameEvent(GameEvent ge){
        if(ID==ge.getID()){
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    public void passEvent(GameEvent ge, Handler handler){
        handler.notify(ge);
    }
    
}
