
package gui;

import dialogues.Dialogue;
import java.io.IOException;
import level.Area;
import level.Dungeon;
import listeners.DepthListener;
import logic.ImageUtils;

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
    
    public static void main(String... args) throws IOException{
        Game game = new Game();
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
