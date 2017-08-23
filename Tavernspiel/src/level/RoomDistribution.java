
package level;

import java.io.Serializable;
import logic.Distribution;
import static logic.Distribution.r;

/**
 *
 * @author Adam Whittaker
 */
public class RoomDistribution implements Serializable{
    
    public interface MakeRoom{
        Room make(Stage st);
    };
    protected final MakeRoom[] roomMethods;
    protected final int[] chances;
    
    public RoomDistribution(MakeRoom[] rMethods, int[] cha){
        chances = Distribution.convert(cha);
        roomMethods = rMethods;
    }
    
    /**
     * Gives a random output from this Distribution's output array based on its
     * chances.
     * @return An output from the array.
     */
    public MakeRoom next(){
        return roomMethods[chanceToInt(r.nextInt(chances[chances.length-1])+1)];
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
