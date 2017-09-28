
package gui;

import animation.GameObjectAnimator;
import creatureLogic.Attributes;
import creatures.Hero;
import javax.swing.ImageIcon;
import level.Area;
import level.Dungeon;
import listeners.DepthListener;
import logic.ImageUtils;
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
        GameObjectAnimator goa = new GameObjectAnimator(ImageUtils.addImageBuffer(new ImageIcon("graphics/spritesheets/tree.png")),
                new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5});
        Hero hero = new Hero(new Attributes(), goa);
        hero.x = 1;
        hero.y = 1;
        Window.main.currentArea.addObject(hero);
        //new Dialogue("Dialogue", "offCase", false, new CSlider("Temperature", 0, 0, 2, 5, 1)).action(game);
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
