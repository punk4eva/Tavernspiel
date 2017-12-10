
package ai.intelligence;

import ai.AITemplate;
import creatures.Creature;
import level.Area;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * An AI of intelligence 1. Capable or attacking an enemy and basic pathfinding.
 */
public class IntelligentAI1 extends AITemplate{
    
    public EnState state;

    @Override
    @Unfinished
    public void turn(Creature c, Area area){
        if(skipping>0){
            skipping-=c.attributes.speed;
            if(skipping<0){
                skipping = 0;
            }
        }else if(c.x!=c.attributes.ai.destinationx||
                c.y!=c.attributes.ai.destinationy){
            decideAndMove(c);
            return;
        }
        throw new UnsupportedOperationException("Not Supported Yet!");
    }
    
    public static enum EnState{
        HUNTING, WANDERING, SLEEPING, FLEEING, AMOK
    }
    
}
