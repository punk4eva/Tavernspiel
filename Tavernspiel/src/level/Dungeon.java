
package level;

import creatures.Hero;
import gui.Game;
import gui.Window;
import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a collection of Stages.
 */
public class Dungeon implements Serializable{
    
    private final static long serialVersionUID = -619892070;
    
    private final Game game;
    protected int depth = -1;
    protected Stage[] stages;
    public Area currentArea;
    
    /**
     * Creates a new instance.
     * @param g The Game.
     */
    public Dungeon(Game g){
        game = g;
        stages = new Stage[5];
        Location loc = new Location("Shkoder", "shkoderTileset", "water", "Cyanoshrooms.wav", 2);
        loc.roomDistrib = new RoomDistribution(loc, 3, 12);
        stages[0] = new Stage(loc, 5, new String[]{"the upper level of the caves", "the lower level of the caves"}, null);
        stages[0].areas[0] = stages[0].areaBuilder.load(stages[0].location.roomDistrib, 0); depth=1;stages[0].loadedLevel=1;
        g.currentArea = getArea2();
        //stages[0].areas[0] = Area.getPreloadedArea("filetesting/phallus.map");
    }
    
    /**
     * Descends to the next depth.
     * @param hero
     */
    public void descend(Hero hero){
        depth++;
        if(!getStage().isLoaded(depth)){
            getStage().loadNext();
        }
        game.updateDepth(getArea2());
        if(hero!=null) currentArea.addHero(hero, true);
    }
    
    /**
     * Ascends to the previous depth.
     * @param hero
     */
    public void ascend(Hero hero){
        if(depth!=1){
            depth--;
            game.updateDepth(getArea2());
            currentArea.addHero(hero, false);
        }
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
        if(currentArea==null){
            int roll = depth;
            for(int n=0;true;n++){
                if(roll<=stages[n].length){
                    currentArea = stages[n].areas[roll-1];
                    break;
                }
                else roll-=stages[n].length;
            }
        }
        return currentArea;
    }
    
    private Area getArea2(){
        int roll = depth;
        for(int n=0;true;n++){
            if(roll<=stages[n].length){
                currentArea = stages[n].areas[roll-1];
                break;
            }
            else roll-=stages[n].length;
        }
        return currentArea;
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
