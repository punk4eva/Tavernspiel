
package level;

import java.io.Serializable;
import logic.Distribution;
import static logic.Distribution.r;

/**
 *
 * @author Adam Whittaker
 */
public class RoomDistribution implements Serializable{
    
    private final static long serialVersionUID = 1600386847;
    
    public interface MakeRoom{
        Room make(Location loc);
    };
    private final MakeRoom[] roomMethods, lockedRoomMethods;
    protected final int[] chances, lockedChances;
    private final Location location;
    
    public RoomDistribution(Location loc, MakeRoom[] rMethods, MakeRoom[] lMethods, int[] cha, int[] lCha){
        chances = Distribution.convert(cha);
        lockedRoomMethods = lMethods;
        location = loc;
        lockedChances = Distribution.convert(lCha);
        roomMethods = rMethods;
    }
    
    /**
     * Generates a random output from this Distribution's output array based on its
     * chances.
     * @return A randomly generated Room.
     */
    public Room next(){
        return roomMethods[chanceToInt(r.nextInt(chances[chances.length-1])+1)].make(location);
    }
    
    /**
     * Generates a random output from this Distribution's output array based on its
     * chances.
     * @return A randomly generated locked Room.
     */
    public Room nextLocked(){
        return lockedRoomMethods[lockedChanceToInt(r.nextInt(lockedChances[lockedChances.length-1])+1)].make(location);
    }
    
    /**
     * Gets the index of output which the given chance value obtains.
     * @param i The chance value.
     * @return The index of the output.
     */
    private int chanceToInt(int i){
        for(int n=0;n<chances.length;n++) if(i<=chances[n]) return n;
        return -1;
    }
    
    /**
     * Gets the index of output which the given chance value obtains.
     * @param i The chance value.
     * @return The index of the output.
     */
    private int lockedChanceToInt(int i){
        for(int n=0;n<lockedChances.length;n++) if(i<=lockedChances[n]) return n;
        return -1;
    }
    
}
