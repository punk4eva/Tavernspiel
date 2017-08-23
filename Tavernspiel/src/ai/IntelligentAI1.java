
package ai;

/**
 *
 * @author Adam Whittaker
 * 
 * An AI of intelligence 1. Capable or attacking an enemy and basic pathfinding.
 */
public class IntelligentAI1 extends AITemplate{
    
    protected EnState state;
    
    protected enum EnState{
        HUNTING, WANDERING, SLEEPING, FLEEING, AMOK
    }
    
}
