package gui;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;
import listeners.DeathEvent;
import listeners.DeathListener;
import listeners.GameEvent;
import listeners.GrimReaper;
import logic.GameObject;

/**
 *
 * @author Charlie Hands
 */
public class Handler implements DeathListener, Serializable{
    LinkedList<GameObject> object = new LinkedList<>();
    
    /**
     * Creates a new Handler object.
     * @param gr The GrimReaper to listen to.
     */
    public Handler(GrimReaper gr){
        gr.addDeathListener(this);
    }

    /**
    public void tick(){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }
    */

    /**
     * Renders each currently active GameObject.
     * @param g The graphics to draw on.
     */
    public void render(Graphics g){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }
    
    /**
     * Tells all current game objects that a turn happened.
     * @param delta The fraction of a turn consumed.
     */
    public void turn(double delta){
        object.stream().forEach(o -> {
            GameObject tempObject = o; //I don't know why we do this. It's not even a solution to a bug.
            tempObject.turn(delta);
        });
    }

    /**
     * Adds an object to the list of objects in game.
     * @param object The object to be added.
     */
    public void addObject(GameObject object){
        this.object.add(object);
    }

    /**
     * Removes an object to the list of objects in game.
     * @param object The object to be removed.
     */
    public void removeObject(GameObject object){
        this.object.remove(object);
    }
    
    /**
     * Notifies all active GameObjects of an event.
     * @param ge The Event to be broadcast.
     */
    public void notify(GameEvent ge){
        object.stream().forEach(gameOb -> {
            gameOb.gameEvent(ge);
        });
    }

    @Override
    public void lifeTaken(DeathEvent de){
        removeObject(de.getCreature());
    }

}
