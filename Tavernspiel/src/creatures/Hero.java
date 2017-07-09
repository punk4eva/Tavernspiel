
package creatures;

import animation.Animation;
import creatureLogic.Attributes;

/**
 *
 * @author Adam Whittaker
 */
public class Hero extends Creature{
    
    public int exp = 0;
    
    public Hero(Attributes atb, Animation an, int ac){
        super("Hero", "UNWRITTEN", atb, an, ac);
    }
    
}
