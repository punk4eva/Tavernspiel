
package level;

import creatures.Hero;
import gui.Game;
import gui.Window;
import items.builders.PotionBuilder;
import items.builders.RingBuilder;
import items.builders.ScrollBuilder;
import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a collection of Stages.
 */
public class Dungeon implements Serializable{
    
    private final static long serialVersionUID = -619892070;
    
    private transient Game game;
    protected int depth = -1;
    protected Stage[] stages;
    public Area currentArea;
    private final ScrollBuilder scrollBuilder = new ScrollBuilder();
    private final PotionBuilder potionBuilder = new PotionBuilder();
    private final RingBuilder ringBuilder = new RingBuilder();
    
    /**
     * Creates a new instance.
     * @param g The Game.
     */
    public Dungeon(Game g){
        game = g;
        stages = new Stage[5];
    }
    
    public final void initialize(Game g, Location loc){
        stages[0] = new Stage(loc, 5, new String[]{"the upper level of the caves", "the lower level of the caves"}, null);
        stages[0].areas[0] = stages[0].areaBuilder.load(stages[0].location.roomDistrib, 0);
        depth = 1;
        stages[0].loadedLevel = 1;
        g.setArea(getArea());
    }
    
    /**
     * Sets the Game.
     * @param g The Game.
     */
    public final void setGame(Game g){
        game = g;
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
        game.updateArea(getArea());
        if(hero!=null) currentArea.addHero(hero, true);
    }
    
    public void setArea(Hero hero, Area area){
        area.addHero(hero, true);
    }
    
    /**
     * Ascends to the previous depth.
     * @param hero
     */
    public void ascend(Hero hero){
        if(depth!=1){
            depth--;
            game.updateArea(getArea());
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
    
    private Area getArea(){
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
    
    /**
     * A static retrieval method for the PotionBuilder.
     * @return
     */
    public static PotionBuilder potionBuilder(){
        return ((Game)Window.main).dungeon.potionBuilder;
    }
    
    /**
     * A static retrieval method for the ScrollBuilder.
     * @return
     */
    public static ScrollBuilder scrollBuilder(){
        return ((Game)Window.main).dungeon.scrollBuilder;
    }
    
    /**
     * A static retrieval method for the RingBuilder.
     * @return
     */
    public static RingBuilder ringBuilder(){
        return ((Game)Window.main).dungeon.ringBuilder;
    }
    
}
