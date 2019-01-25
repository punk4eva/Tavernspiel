
package ai;

import containers.PhysicalReceptacle;
import creatures.Creature;
import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.BrokenBarrierException;
import level.Area;
import pathfinding.Point;

/**
 *
 * @author Adam Whittaker
 * 
 * Handles behavior of creatures.
 */
public abstract class AITemplate implements Serializable{
    
    private final static long serialVersionUID = 54431988;
    
    public EnType type; //The type of AI
    public int intelligence = 3; //The intelligence
    public volatile double skipping = 0;
    public MagicHexagon magic = new MagicHexagon(); //The AI's inate magic abilities.
    public int destinationx = -1, destinationy = -1; //The destination coords of the AI.
    public Iterator<Point> currentPath;
    public AIBaseActions BASEACTIONS = new AIBaseActions(); //The basic actions that the ai can do.
    public int[] moving;
    public boolean restrained = false;

    /**
     * Sets the AI's destination coords.
     * @param x
     * @param y
     */
    public void setDestination(int x, int y){
        if(!restrained){
            destinationx = x;
            destinationy = y;
        }
    }
    
    /**
     * Follows the current path.
     * @param c The Creature.
     */
    public void decideAndMove(Creature c){
        if(currentPath==null){
            currentPath = c.area.graph.searcher.findExpressRoute(new Point(c.x, c.y), new Point(destinationx, destinationy)).iterator();
            c.changeAnimation("move");
            currentPath.next();
        }
        Point next = currentPath.next();
        try{
            c.motionBarrier.await();
        }catch(InterruptedException | BrokenBarrierException e){}
        BASEACTIONS.smootheRaw(c, next.x, next.y);
        if(!currentPath.hasNext()){
            currentPath = null;
            c.changeAnimation("stand");
            PhysicalReceptacle r = c.area.getReceptacle(c.x, c.y); 
            if(r!=null) r.interact(c, c.area);
        }
    }
    
    /**
     * The type of AI.
     */
    public enum EnType{
        PREDEFINED, HANDICAPPED, NORMAL, RANGED
    };   
    
    /**
     * Decides what to do and does it.
     * @param c The creature who owns this AI.
     * @param area The area that the creature is in.
     */
    public abstract void turn(Creature c, Area area);
    
    /**
     * Paralyzes the Creature for the given duration of turns.
     * @param duration
     */
    public void paralyze(double duration){
        skipping += duration;
    }
    
}
