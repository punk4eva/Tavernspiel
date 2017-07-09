package gui;

import java.awt.Graphics;
import java.util.LinkedList;
import listeners.GameEvent;
import logic.GameObject;

/**
 *
 * @author Charlie Hands
 */
public class Handler{
    LinkedList<GameObject> object = new LinkedList<>();

    public void tick(){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }
    }

    public void render(Graphics g){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }
    
    public void notify(GameEvent ge){
        object.stream().forEach(gameOb -> {
            gameOb.gameEvent(ge);
        });
    }

}
