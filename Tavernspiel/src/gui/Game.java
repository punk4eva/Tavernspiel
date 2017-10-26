
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
import pathfinding.CorridorBuilder;
import pathfinding.Path;
import pathfinding.Point;

/**
 *
 * @author Adam Whittaker
 * 
 * Plays the Game.
 */
public class Game extends MainClass implements DepthListener{
    
    public final Dungeon dungeon;
    
    
    /**
     * Starts the game.
     */
    public Game(){
        dungeon = new Dungeon(this);
        dungeon.descend(player);
        window = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
    }
    
    public static void main(String... args){
        Game game = new Game();
        GameObjectAnimator goa = new GameObjectAnimator(ImageUtils.addImageBuffer(new ImageIcon("graphics/spritesheets/tree.png")),
                new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5});
        Hero hero = new Hero(new Attributes(), goa);
        hero.x = Window.main.currentArea.startCoords[0];
        hero.y = Window.main.currentArea.startCoords[1];
        Window.main.currentArea.addObject(hero);
        CorridorBuilder builder = new CorridorBuilder(Window.main.currentArea);
        builder.buildCorridor(new Path(new Point(5, 5), new Point(5, 6), new Point(5, 7), new Point(5, 8), new Point(5, 9), new Point(5, 10), 
                new Point(5, 11), new Point(6, 11), new Point(7, 11), new Point(8, 11), new Point(9, 11), new Point(10, 11), new Point(11, 11), new Point(12, 11)));
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
