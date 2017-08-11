
package gui;

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
        dungeon = new Dungeon(this);
        dungeon.descend();
        soundSystem.playAbruptLoop(currentArea.location.backgroundMusicPath);
    }
    
    public static void main(String... args){
        Game game = new Game();
    }

    @Override
    public void updateDepth(Area area){
        currentArea = area;
        messageQueue.add("You are now in " + dungeon.getDepthClassifier() + ".");
        soundSystem.playSFX("Misc/newDepth.wav"); //@unfinished
        soundSystem.playAbruptLoop(currentArea.location.backgroundMusicPath);
    }
    
}
