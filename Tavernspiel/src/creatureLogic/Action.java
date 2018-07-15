
package creatureLogic;

import gui.Window;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents an action taken by a Creature.
 */
public abstract class Action implements Runnable{
    
    public double turns;
    
    /**
     * Creates a new instance defaulting to one hero speed turn.
     */
    public Action(){
        this(Window.main.player.attributes.speed);
    }
    
    /**
     * Creates a new instance.
     * @param t The number of turns this action takes.
     */
    public Action(double t){
        turns = t;
    }
    
}
