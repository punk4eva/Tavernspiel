
package gui;

import gui.mainToolbox.Main;
import ai.PlayerAI;
import blob.ParticleAnimation;
import containers.HeroInventory;
import creatureLogic.Attributes;
import creatures.Hero;
import items.Apparatus;
import items.misc.Gold;
import level.Area;
import level.Dungeon;
import level.Location;
import logic.ConstantFields;
import logic.FileHandler;
import logic.GameSettings;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * 
 * Plays the Game.
 */
public final class Game extends Main{
    
    public final Dungeon dungeon;
    public String savePath = "filetesting/game.ser";
    
    
    /**
     * Starts the game.
     */
    public Game(){
        window = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
        dungeon = new Dungeon(this);
        dungeon.initialize(this, Location.SHKODER_LOCATION);
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
     * @param dun The Dungeon
     */
    public Game(Dungeon dun){
        window = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
        dungeon = dun;
        dungeon.setGame(this);
        gui.addMessage("You are now in " + dungeon.getDepthClassifier() + ".");
        setArea(dungeon.currentArea);
        player = currentArea.hero;
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
        game.currentArea = Area.getPreloadedArea("preload/interiorTest.template");
        game.player.setXY(game.currentArea.startCoords[0], game.currentArea.startCoords[1]);
        /*game.currentArea.plop(Apparatus.getRandomMeleeWeapon(1, game.currentArea.location), game.player.x, game.player.y);
        game.currentArea.plop(new Gold(100), game.player.x+1, game.player.y);
        //game.save();
        //Game game = FileHandler.deserializeGame("filetesting/game.ser");
        System.out.println("Running");
        ParticleAnimation a = GameSettings.FIRE_SETTING.get(ConstantFields.fireColor, ConstantFields.fireTrailColor);
        a.setXY(100, 100);
        Main.animator.addAnimation(a);
        //game.currentArea.graph.navMesh = new NavigationMesh(game.currentArea.graph, game.currentArea);*/
    }

    @Unfinished("The sfx for newDepth.")
    public void updateArea(Area area){
        setArea(area);
        gui.addMessage("You are now in " + dungeon.getDepthClassifier() + ".");
        soundSystem.playSFX("Misc/newDepth.wav"); //@unfinished
        soundSystem.playAbruptLoop(currentArea.location.feeling.backgroundMusicPath);
    }

    /**
     * Saves the game.
     */
    public void save(){
        FileHandler.serializeGame(this);
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
