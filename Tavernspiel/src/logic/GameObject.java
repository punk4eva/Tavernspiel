
package logic;

import animation.Animation;
import gui.Handler;
import gui.MainClass;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import listeners.GameEvent;

/**
 *
 * @author Adam Whittaker
 * 
 * @TOPLEVEL
 */
public abstract class GameObject implements ActionListener{
    
    public int ID;
    public String name;
    public String description;
    public Animation animation;
    public int areaCode;
    
    public GameObject(String n, String desc, Animation an, int ac){
        ID = MainClass.idhandler.genID();
        name = n;
        description = desc;
        animation = an;
        areaCode = ac;
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

    //I don't know whether this should be here.
    @Override
    public void actionPerformed(ActionEvent ae){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    public void gameEvent(GameEvent ge){
        if(ID==ge.getID()){
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    public void passEvent(GameEvent ge, Handler handler){
        handler.notify(ge);
    }
    
}
