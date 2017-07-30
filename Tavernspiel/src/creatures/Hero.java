
package creatures;

import animation.Animation;
import creatureLogic.Attributes;
import java.awt.Graphics;

/**
 *
 * @author Adam Whittaker
 */
public class Hero extends Creature{
    
    public int hunger = 100;
    
    public Hero(Attributes atb, Animation an, int ac){
        super("Hero", "UNWRITTEN", atb, an, ac);
    }

    @Override
    public void turn(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(Graphics g){
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}
