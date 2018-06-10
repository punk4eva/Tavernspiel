
package gui;

import gui.mainToolbox.Main;
import ai.PlayerAI;
import animation.GameObjectAnimator;
import creatureLogic.Attributes;
import creatures.Hero;
import items.Apparatus;
import javax.swing.ImageIcon;
import level.Area;
import level.Dungeon;
import logic.ImageUtils;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * Plays the Game.
 */
public class Game extends Main{
    
    public final Dungeon dungeon;
    
    
    /**
     * Starts the game.
     */
    public Game(){
        window = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
        dungeon = new Dungeon(this);
        gui.addMessage("You are now in " + dungeon.getDepthClassifier() + ".");
        GameObjectAnimator goa = new GameObjectAnimator(ImageUtils.addImageBuffer(new ImageIcon("graphics/spritesheets/tree.png")),
                new String[]{"stand", "move", "attack", "die"}, new int[]{2, 4, 8, 5});
        player = new Hero(new Attributes(), goa);
        currentArea.addHero(player, true);
        addKeyListener((PlayerAI) player.attributes.ai);
        hud = new HUD(player.inventory.quickslot);
        start();
    }
    
    //Progenitor Thread
    public static void main(String... args){
        Thread.currentThread().setName("Progenitor Thread");
        Game game = new Game();
        try{
            Thread.sleep(1200);
        }catch(InterruptedException e){}
        game.currentArea.plop(Apparatus.getRandomMeleeWeapon(1, game.currentArea.location), game.player.x, game.player.y);
    }

    @Unfinished("The sfx for newDepth.")
    public void updateDepth(Area area){
        currentArea = area;
        gui.addMessage("You are now in " + dungeon.getDepthClassifier() + ".");
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
