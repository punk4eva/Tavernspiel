
package gui;

import ai.PlayerAI;
import animation.GameObjectAnimator;
import creatureLogic.Attributes;
import creatures.Hero;
import dialogues.Dialogue;
import guiUtils.*;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import level.Area;
import level.Dungeon;
import logic.ImageUtils;
import logic.Utils.Unfinished;
import pathfinding.CorridorBuilder;
import pathfinding.Path;
import pathfinding.Point;
import pathfinding.Searcher;

/**
 *
 * @author Adam Whittaker
 * 
 * Plays the Game.
 */
public class Game extends MainClass{
    
    public final Dungeon dungeon;
    
    
    /**
     * Starts the game.
     */
    public Game(){
        GameObjectAnimator goa = new GameObjectAnimator(ImageUtils.addImageBuffer(new ImageIcon("graphics/spritesheets/tree.png")),
                new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5});
        dungeon = new Dungeon(this);
        dungeon.descend(player);
        player = new Hero(new Attributes(), goa);
        currentArea.addHero(player);
        window = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
        addKeyListener((PlayerAI) player.attributes.ai);
        hud = new HUD();
    }
    
    public static void main(String... args){
        Game game = new Game();
        Window.main.currentArea.addHero(game.player);
        /*Window.main.currentArea = new Area(new Dimension(80, 80), Window.main.currentArea.location);
        CorridorBuilder builder = new CorridorBuilder(Window.main.currentArea);
        Path path = new Searcher(Window.main.currentArea.graph).findNullPath(new Point(5, 5), new Point(14, 13));
        builder.buildCorridor(path);*/
        /*Dialogue dialogue = new Dialogue("Test", "offCase", true, new CComponent[]{
        new CButton("Button", 0, 0, 8, null),
        new CCheckbox("Checkbox", 0, 0, null),
        new CSlider("Slider", 0, 0, 1, 7, 1)});
        dialogue.action(game);*/
    }

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
