
package logic;

import animation.Animation;
import gui.MainClass;

/**
 *
 * @author Adam Whittaker
 * 
 * @TOPLEVEL
 */
public class GameObject{
    
    public int ID;
    public String name;
    public String description;
    public Animation animation;
    
    public GameObject(String n, String desc, Animation an){
        ID = MainClass.idhandler.genID();
        name = n;
        description = desc;
        animation = an;
    }
    
    public void turn(){
        //@charlie
    }
    
    public void tick(){
        //@charlie
    }
    
}
