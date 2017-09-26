
package ai;

import creatures.Creature;
import level.Area;

/**
 *
 * @author Adam Whittaker
 * 
 * An AI of intelligence 1. Capable or attacking an enemy and basic pathfinding.
 */
public class IntelligentAI1 extends AITemplate{
    
    protected EnState state;

    @Override
    public void turn(Creature c, Area area){
        throw new UnsupportedOperationException("Turn not initialized.");
    }
    
    protected enum EnState{
        HUNTING, WANDERING, SLEEPING, FLEEING, AMOK
    }
    
}
