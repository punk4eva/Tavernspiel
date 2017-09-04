
package creatureLogic;

import creatures.Creature;
import java.io.Serializable;
import logic.Distribution;
import static logic.Distribution.r;

/**
 *
 * @author Adam Whittaker
 * 
 * A variation of Distribution but for creatures.
 */
public class CreatureDistribution implements Serializable{

    protected final Creature[] creatures;
    protected final int[] chances;

    /**
     * @see Distribution.new
     */
    public CreatureDistribution(Creature[] out, int[] cha){
        chances = Distribution.convert(cha);
        creatures = out;
    }
    
    /**
     * Gives a random output from this Distribution's output array based on its
     * chances.
     * @return An output from the array.
     */
    public Creature next(){
        return creatures[chanceToInt(r.nextInt(chances[chances.length-1])+1)];
    }
    
    /**
     * Gets the index of output which the given chance value obtains.
     * @param i The chance value.
     * @return The index of the output.
     */
    protected int chanceToInt(int i){
        for(int n=0;n<chances.length;n++) if(i<=chances[n]) return n;
        return -1;
    }
    
}
