
package gui;

import dialogues.Dialogue;
import dialogues.PauseMenu;
import guiUtils.CSlider;
import level.Area;
import level.Dungeon;
import listeners.DepthListener;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * Plays the Game.
 */
public class Game extends MainClass implements DepthListener{
    
    Dungeon dungeon;
    
    /**
     * Starts the game.
     */
    public Game(){
        dungeon = new Dungeon(this, handler);
        dungeon.descend();
        window = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
    }
    
    public static void main(String... args){
        Game game = new Game();
        new Dialogue("Dialogue", "offCase", false, new CSlider("Temperature", 0, 0, 2, 5, 1)).action(game);
    }

    @Override
    @Unfinished("The sfx for newDepth.")
    public void updateDepth(Area area){
        currentArea = area;
        messageQueue.add("You are now in " + dungeon.getDepthClassifier() + ".");
        soundSystem.playSFX("Misc/newDepth.wav"); //@unfinished
        soundSystem.playAbruptLoop(currentArea.location.backgroundMusicPath);
    }

    /**
     * Saves the game.
     */
    @Unfinished
    public void save(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Ends the game.
     */
    @Unfinished
    public void endGame(){
        stop();
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
