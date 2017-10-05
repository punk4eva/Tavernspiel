
package level;

import gui.Handler;
import java.io.Serializable;
import level.RoomDistribution.MakeRoom;
import listeners.DepthListener;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a collection of Stages.
 */
public class Dungeon implements Serializable{
    
    private final static long serialVersionUID = -619892070;
    
    private final DepthListener depthListener;
    protected int depth = -1;
    private final Handler handler;
    protected Stage[] stages;
    
    /**
     * Creates a new instance.
     * @param dl The DepthListener.
     * @param hand The Handler.
     */
    public Dungeon(DepthListener dl, Handler hand){
        depthListener = dl;
        handler = hand;
        stages = new Stage[5];
        Location loc = new Location("Shkoder", "shkoderTileset", "water", "Cyanoshrooms.wav");
        loc.roomDistrib = new RoomDistribution[]{new RoomDistribution(loc, new MakeRoom[]{(loca, items) -> RoomBuilder.standard(loca, hand)}, null, new int[]{1}, new int[]{1})};
        stages[0] = new Stage(loc, 5, new String[]{"The upper level of the caves"}, null);
        stages[0].areas[0] = stages[0].areaBuilder.load(stages[0].location.roomDistrib[0]); depth=0;stages[0].loadedLevel=3;
    }
    
    /**
     * Descends to the next depth.
     */
    public void descend(){
        depth++;
        if(!getStage().isLoaded(depth)){
            getStage().loadNext();
        }
        depthListener.updateDepth(getArea());
    }
    
    /**
     * Ascends to the previous depth.
     */
    public void ascend(){
        depth--;
        depthListener.updateDepth(getArea());
    }
    
    /**
     * Gets the current Stage.
     * @return The current Stage.
     */
    public Stage getStage(){
        int roll = depth;
        for(int n=0;true;n++){
            if(roll<=stages[n].length) return stages[n];
            else roll-=stages[n].length;
        }
    }
    
    /**
     * Gets the current Area.
     * @return The current Area.
     */
    public Area getArea(){
        int roll = depth;
        for(int n=0;true;n++){
            if(roll<=stages[n].length) return stages[n].areas[roll-1];
            else roll-=stages[n].length;
        }
    }

    /**
     * Gets the current level in the Stage.
     * @return The current level in the Stage.
     */
    public int getLevelInStage(){
        int roll = depth;
        for(int n=0;true;n++){
            if(roll<=stages[n].length) return roll;
            else roll-=stages[n].length;
        }
    }
    
    /**
     * Gets the current depth classifier.
     * @return The current depth classifier.
     */
    public String getDepthClassifier(){
        int roll = depth;
        for(int n=0;true;n++){
            if(roll<=stages[n].length) return stages[n].depthClassifiers[roll-1];
            else roll-=stages[n].length;
        }
    }
    
}
