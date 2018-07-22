
package gui;

import gui.mainToolbox.Main;
import ai.PlayerAI;
import containers.HeroInventory;
import creatureLogic.Attributes;
import creatures.Hero;
import items.Apparatus;
import items.misc.Gold;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import level.Area;
import level.Dungeon;
import logic.FileHandler;
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
        player = new Hero(new Attributes());
        gui.hero = player;
        currentArea.addHero(player, true);
        addKeyListener((PlayerAI) player.attributes.ai);
        gui.hud = new HUD(((HeroInventory)player.inventory).quickslot);
        resetGUIScreens();
        start();
    }
    
    /**
     * Starts the game from serialized context.
     * @param hero The Hero
     * @param dun The Dungeon
     */
    private Game(Hero hero, Dungeon dun){
        window = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
        dungeon = dun;
        dungeon.setGame(this);
        gui.addMessage("You are now in " + dungeon.getDepthClassifier() + ".");
        player = hero;
        gui.hero = player;
        currentArea.addHero(player, true);
        addKeyListener((PlayerAI) player.attributes.ai);
        gui.hud = new HUD(((HeroInventory)player.inventory).quickslot);
        resetGUIScreens();
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
        game.currentArea.plop(new Gold(100), game.player.x+1, game.player.y);
        FileHandler.serialize(game, "filetesting/game.ser");
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
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        Dungeon dun = (Dungeon) in.readObject();
        Hero hero = (Hero) in.readObject();
        new Game(hero, dun);
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException{
        out.writeObject(dungeon);
        out.writeObject(player);
    }
    
}
