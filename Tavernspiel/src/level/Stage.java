
package level;

import creatureLogic.CreatureDistribution;

/**
 *
 * @author Adam Whittaker
 */
public class Stage{
    
    protected Area[] areas;
    protected RoomDistribution roomDistrib = RoomBuilder.getNormalRoomDistribution();
    protected final CreatureDistribution[] spawnDistribution;
    public final Location location;
    public final int length;
    protected String[] depthClassifiers; //words such as "depth" or "the throne room"
    protected int loadedLevel = 0;
    protected AreaBuilder areaBuilderForNextUnloaded;
    
    public Stage(Location loc, int l, String[] depthClass, CreatureDistribution[] distrib){
        location = loc;
        areas = new Area[l];
        spawnDistribution = distrib;
        length = l;
        depthClassifiers = depthClass;
        areaBuilderForNextUnloaded = new AreaBuilder(location);
    }
    
    public boolean isLoaded(int depth){
        return depth <= loadedLevel;
    }
    
    public void loadNext(){
        if(areas[loadedLevel]==null){
            areas[loadedLevel] = areaBuilderForNextUnloaded.load();
        }else throw new IllegalStateException("Cannot load preloaded area.");
        loadedLevel++;
        areaBuilderForNextUnloaded.flush();
    }
    
}
