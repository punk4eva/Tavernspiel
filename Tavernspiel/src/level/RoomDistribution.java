
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
        Room make(Location loc, int depth);
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
     * @param depth
     * @return A randomly generated Room.
     */
    public Room next(int depth){
        return roomMethods[chanceToInt(r.nextInt(chances[chances.length-1])+1)].make(location, depth);
    }
    
    /**
     * Generates a random output from this Distribution's output array based on its
     * chances.
     * @param depth The depth of the Room.
     * @return A randomly generated locked Room.
     */
    public Room nextLocked(int depth){
        return lockedRoomMethods[lockedChanceToInt(r.nextInt(lockedChances[lockedChances.length-1])+1)].make(location, depth);
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
    
    public static RoomDistribution[] testItemless(Location loc, int n){
        RoomDistribution ret[] = new RoomDistribution[n];
        for(int j=0;j<n;j++) ret[j] = new RoomDistribution(loc, 
                new MakeRoom[]{(loca, d) -> RoomBuilder.itemless(loca, d)}, 
                new MakeRoom[]{(loca, d) -> RoomBuilder.lockedItemless(loca, d)}, 
                new int[]{1}, new int[]{1});
        return ret;
    }
    
}
