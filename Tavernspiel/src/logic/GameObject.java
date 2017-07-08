
package logic;

import animation.Animation;
import gui.MainClass;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Adam Whittaker
 * 
 * @TOPLEVEL
 */
public class GameObject implements ActionListener{
    
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
    
    public void render(Graphics g){
        //@charlie
    }

    //I don't know whether this should be here.
    @Override
    public void actionPerformed(ActionEvent ae){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
