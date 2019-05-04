
package gui;

import ai.PlayerAI;
import containers.HeroInventory;
import creatureLogic.Attributes;
import creatures.Hero;
import gui.mainToolbox.Main;
import items.Apparatus;
import items.ItemMap;
import items.misc.Gold;
import level.Area;
import level.Dungeon;
import static level.Dungeon.potionBuilder;
import static level.Dungeon.scrollBuilder;
import level.Location;
import logic.Distribution;
import logic.FileHandler;
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
        dungeon.initialize(Location.SHKODER_LOCATION);
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
        System.out.println("Starting...");
        Game game = new Game();
        /*List<Room> h = new LinkedList<>();
        h.add(RoomBuilder.lottery(Location.SHKODER_LOCATION, 0));
        h.add(RoomBuilder.burntGarden(Location.SHKODER_LOCATION, 0));
        h.add(RoomBuilder.laboratory(Location.SHKODER_LOCATION, 0));
        h.add(RoomBuilder.itemless(Location.SHKODER_LOCATION, 0));
        h.add(RoomBuilder.magicWellRoom(Location.SHKODER_LOCATION, 0));
        RoomStructure r = new RoomStructure.SpiderCorridor(new Dimension(80, 80), Location.SHKODER_LOCATION, h);
        r.generate();
        game.currentArea = r;*/
        
        /*game.currentArea.plop(Apparatus.getRandomMeleeWeapon(0, Location.SHKODER_LOCATION), game.player.x, game.player.y);
        game.currentArea.plop(Gold.getGoldQuantity(2), game.player.x-1, game.player.y);
        game.currentArea.plop(potionBuilder().getRandomPotion(new Distribution(new int[]{}),ItemMap.standardItemMap), game.player.x, game.player.y+1);
        game.currentArea.plop(scrollBuilder().getRandomScroll(ItemMap.standardItemMap.scrollDist), game.player.x, game.player.y-1);*/
        
        //game.currentArea = new AreaGrower(new Dimension(80,80), Location.SHKODER_LOCATION, 0.375,  3,9,  4,9,  4, true).simulate();
        //game.player.setXY(game.currentArea.startCoords[0], game.currentArea.startCoords[1]);
        //game.save();
        //Game game = FileHandler.deserializeGame("filetesting/game.ser");
        /*System.out.println("Running");
        ParticleAnimation a = GameSettings.TORCH_SETTING.get(ConstantFields.fireColor, ConstantFields.fireTrailColor);
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
