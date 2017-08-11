
package level;

import listeners.DepthListener;
import logic.Fileable;

/**
 *
 * @author Adam Whittaker
 */
public class Dungeon implements Fileable{
    
    private final DepthListener depthListener;
    protected int depth = 0;
    protected Stage[] stages;
    
    public Dungeon(DepthListener dl){
        depthListener = dl;
    }
    

    @Override
    public String toFileString(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static Dungeon getFromFileString(String filestring){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void descend(){
        depth++;
        if(!getStage().isLoaded(depth-1)){
            getStage().loadNext();
        }
        depthListener.updateDepth(getArea());
    }
    
    public void ascend(){
        depth--;
        depthListener.updateDepth(getArea());
    }
    
    public Stage getStage(){
        int roll = depth;
        for(int n=0;true;n++){
            if(roll<=stages[n].length) return stages[n];
            else roll-=stages[n].length;
        }
    }
    
    public Area getArea(){
        int roll = depth;
        for(int n=0;true;n++){
            if(roll<=stages[n].length) return stages[n].areas[roll-1];
            else roll-=stages[n].length;
        }
    }

    public int getLevelInStage(){
        int roll = depth;
        for(int n=0;true;n++){
            if(roll<=stages[n].length) return roll;
            else roll-=stages[n].length;
        }
    }
    
    public String getDepthClassifier(){
        int roll = depth;
        for(int n=0;true;n++){
            if(roll<=stages[n].length) return stages[n].depthClassifiers[roll-1];
            else roll-=stages[n].length;
        }
    }
    
}
