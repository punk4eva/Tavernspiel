
package gui;

import dialogues.Dialogue;
import level.Area;
import level.Dungeon;
import listeners.DepthListener;

/**
 *
 * @author Adam Whittaker
 */
public class Game extends MainClass implements DepthListener{
    
    Dungeon dungeon;
    
    public Game(){
        dungeon = new Dungeon(this, handler);
        dungeon.descend();
        window = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
    }
    
    public static void main(String... args){
        Game game = new Game();
        Dialogue dia = new Dialogue("Test", null, "Option 1", "Option 2", "Option 3");
    }

    @Override
    public void updateDepth(Area area){
        currentArea = area;
        messageQueue.add("You are now in " + dungeon.getDepthClassifier() + ".");
        soundSystem.playSFX("Misc/newDepth.wav"); //@unfinished
        soundSystem.playAbruptLoop(currentArea.location.backgroundMusicPath);
    }

    public void save(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
