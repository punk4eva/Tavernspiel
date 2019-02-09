
package gui.mainToolbox;

import animation.MiscAnimator;
import creatures.Hero;
import dialogues.DialogueBase;
import gui.Viewable;
import gui.Window;
import static gui.mainToolbox.MouseInterpreter.*;
import gui.pages.Page;
import items.Item;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.function.Predicate;
import level.Area;
import logic.SoundHandler;
import logic.Utils.Unfinished;
import testUtilities.TestUtil;


/**
 *
 * @author Adam Whittaker
 * 
 * The kernel of the game. Manages threads and controls access to different 
 * managers such as the GuiBase.
 */
public abstract class Main extends Canvas implements Runnable, Page{
    
    public static final int WIDTH, HEIGHT;
    public transient static PrintStream exceptionStream, performanceStream;
    static{
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int)screen.getWidth();
        HEIGHT = (int)screen.getHeight();
        try{
            exceptionStream = new PrintStream(new File("log/exceptions.txt"));
            performanceStream = new PrintStream(new File("log/performance.txt"));
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }        
    }

    protected volatile boolean running = false;
    protected Thread runThread;
    public TurnThread turnThread;
    private final KeyProcessor keyProcessor = new KeyProcessor();
    protected Window window;
    protected Page page;
    public PageFlipper pageFlipper;

    public static final SoundHandler soundSystem = new SoundHandler();
    public Pacemaker pacemaker;
    public final static MiscAnimator animator = new MiscAnimator();
    protected static final GuiBase gui = new GuiBase();
    protected MouseInterpreter mouse = new MouseInterpreter();
    protected volatile Area currentArea;
    public volatile Hero player;
    
    /**
     * Creates an instance.
     */
    protected Main(){
        pacemaker = new Pacemaker(this);
        super.addKeyListener(keyProcessor);
    }
    
    /**
     * Tells the the TurnThread that the given number of turns have passed.
     * @param t
     * @deprecated DANGEROUS
     */
    public void setTurnsPassed(double t){
        turnThread.setTurnsPassed(t);
    }
    
    /**
     * Forwards the method.
     * @param t
     */
    public void addTurnsPassed(double t){
        turnThread.addTurnsPassed(t);
    }
    
    /**
     * Adds a Viewable to the display.
     * @param viewable
     */
    public void setViewable(Viewable viewable){
        gui.setViewable(viewable);
    }
    
    /**
     * Adds a Viewable to the display.
     * @param scs
     */
    public void addDraggable(Screen scs){
        gui.addDraggable(scs);
    }
    
    /**
     * Removes the top Viewable from the display.
     */
    public void removeViewable(){
        gui.removeViewable();
    }
    
    /**
     * Sets the SFX volume.
     * @param newVolume The gain in dB.
     */
    public void changeSFXVolume(float newVolume){
        Window.sfxVolume = newVolume;
    }
    
    /**
     * Sets the music volume.
     * @param newVolume The gain in dB.
     */
    public void changeMusicVolume(float newVolume){
        Window.musicVolume = newVolume;
    }
    
    /**
     * Changes the current Dialogue.
     * @param dialogue The new Dialogue.
     */
    public void setDialogue(DialogueBase dialogue){
        gui.setDialogue(dialogue);
        if(dialogue==null) keyProcessor.activateMovementInput();
        else keyProcessor.hijackKeyListener(dialogue);
    }
    
    /**
     * Sets the current Area.
     * @param area The new Area.
     */
    public void setArea(Area area){
        currentArea = area;
        mouse.setFocusBounds(area);
    }
    
    /**
     * Sets the focus based on the tile coordinates.
     * @param tilex
     * @param tiley
     */
    public void setTileFocus(int tilex, int tiley){
        mouse.setTileFocus(tilex, tiley);
    }
    
    /**
     * Sets the focus based on the pixel coordinates.
     * @param x
     * @param y
     */
    public void setPixelFocus(int x, int y){
        mouse.setPixelFocus(x, y);
    }
    
    /**
     * Sets the focus directly (top-left rather than centre).
     * @param x The x pixel
     * @param y The y pixel
     */
    public void setDirectFocus(int x, int y){
        mouse.setDirectFocus(x, y);
    }
    
    /**
     * Adds a flavor text message to the display.
     * @param col The color of the text.
     * @param str The message.
     */
    public static void addMessage(Color col, String str){
        gui.addMessage(col, str);
    }
    
    /**
     * Adds a yellow flavor text message to the display.
     * @param str
     */
    public static void addMessage(String str){
        gui.addMessage(str);
    }
    

    /**
     * @thread render
     */
    @Override
    public void run(){
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
        this.requestFocus();
        pacemaker.start();
    }

    /**
     * Renders the game.
     * @thread render
     */
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        Graphics2D bsg = (Graphics2D) bs.getDrawGraphics();
        page.paint(bsg);
        bsg.dispose();
        bs.show();
    }
    
    @Override
    public void paint(Graphics2D bsg){
        BufferedImage buffer = new BufferedImage((int)(WIDTH/zoom), (int)(HEIGHT/zoom), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        //@Unfinished
        TestUtil.setGraphics(g);
        
        currentArea.paint(g, focusX, focusY);
        currentArea.renderObjects(g, focusX, focusY);
        animator.animate(g, focusX, focusY);
        AffineTransform at = AffineTransform.getScaleInstance(zoom, zoom);
        bsg.drawImage(currentArea.location.backgroundImage.getImage(), 0, 0, null);
        bsg.drawRenderedImage(buffer, at);
        gui.paint(bsg);
        //@Unfinished debug
        //gui.paintScreens(bsg);
        
        g.dispose();
    }
    
    /**
     * Starts the game.
     */
    public synchronized void start(){
        pageFlipper.setPage("main");
        pacemaker = new Pacemaker(this);
        runThread = new Thread(this, "Run Thread");
        runThread.setDaemon(true);
        turnThread = new TurnThread();
        turnThread.setDaemon(true);
        runThread.start();
        turnThread.start();
        keyProcessor.start();
        running = true;
    }

    /**
     * Stops the game.
     */
    public synchronized void stop(){
        try{
            pacemaker.stop();
            runThread.join();
            turnThread.join();
            running = false;
        }catch(InterruptedException e){
            e.printStackTrace(exceptionStream);
        }
    }
    
    /**
     * Toggles the Inventory active state.
     */
    @Unfinished("May be unnecessary")
    public void toggleInventory(){
        gui.toggleInventory();
    }
    
    /**
     * Sets the Inventory active state.
     * @param i Whether to paint the Inventory.
     */
    public void setInventoryActive(boolean i){
        gui.setInventoryActive(i);
    }
    
    /**
     * Sets the Inventory active state.
     * @param i Whether to paint the Inventory.
     * @param pred What Items are "selectable".
     */
    public void setInventoryActive(boolean i, Predicate<Item> pred){
        gui.setInventoryActive(i, pred);
    }
    
    /**
     * Resets the active Screens of the GUI.
     */
    protected final void resetGUIScreens(){
        gui.resetScreens();
    }
    
    @Override
    public void addKeyListener(KeyListener k){
        keyProcessor.listener = k;
        keyProcessor.playerAI = k;
    }
    
}
