
package level;

import creatureLogic.CreatureDistribution;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a collection of Areas and their shared Location, with
 * instructions on how to load the Areas.
 */
public class Stage implements Serializable{
    
    private final static long serialVersionUID = 80204510;
    
    protected Area[] areas;
    public transient Location location;
    public final int length;
    protected String[] depthClassifiers; //words such as "depth" or "the throne room".
    protected int loadedLevel = 0;
    protected AreaBuilder areaBuilder;
    
    /**
     * Creates a new instance.
     * @param loc The Location.
     * @param l The amount of Areas.
     * @param depthClass The array of depth classifiers.
     * @param distrib The array of CreatureDistributions for all Areas.
     */
    public Stage(Location loc, int l, String[] depthClass, CreatureDistribution[] distrib){
        location = loc;
        areas = new Area[l];
        location.spawnDistribution = distrib;
        length = l;
        depthClassifiers = depthClass;
        areaBuilder = new AreaBuilder(location);
    }
    
    /**
     * Checks whether the given depth is loaded. 
     * @param depth The depth to check.
     * @return True if it isn't, false if not.
     */
    public boolean isLoaded(int depth){
        return depth%length < loadedLevel;
    }
    
    /**
     * Loads the next Area.
     */
    public void loadNext(){
        if(areas[loadedLevel]==null){
            areas[loadedLevel] = areaBuilder.load(location.roomDistrib, loadedLevel);
        }else throw new IllegalStateException("Cannot load preloaded area.");
        loadedLevel++;
        areaBuilder.reset();
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        location = Location.LOCATION_MAP.get((String) in.readObject());
    }
    
    private void writeObject(ObjectOutputStream out) 
            throws IOException, ClassNotFoundException{
        out.defaultWriteObject();
        out.writeObject(location.name);
    }
    
}
