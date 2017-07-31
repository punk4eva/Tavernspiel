
package logic;

import animation.Animation;
import buffs.Buff;
import gui.MainClass;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class Gas extends GameObject{
    
    public ArrayList<Buff> buffs = new ArrayList<>();
    public int spreadNumber;
    public int duration = 10;
    
    public Gas(String n, String desc, Buff b, Animation a, int spread, int ac){
        super(n, desc, a, ac);
        buffs.add(b);
        spreadNumber = spread;
    }
    
    public void merge(Gas gas){
        buffs.addAll(gas.buffs);
        spreadNumber = (spreadNumber + gas.spreadNumber)/2;
        duration = (int)((duration + gas.duration)/1.5);
    }
    
    @Override
    public void tick(){
    }
   
    @Override
    public void turn(){
    }

    @Override
    public void render(Graphics g){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
