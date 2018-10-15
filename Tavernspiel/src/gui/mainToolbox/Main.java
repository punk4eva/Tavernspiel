
package gui.mainToolbox;

import animation.MiscAnimator;
import creatureLogic.VisibilityOverlay;
import creatures.Hero;
import dialogues.Dialogue;
import gui.Viewable;
import gui.Window;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import level.Area;
import logic.ConstantFields;
import logic.SoundHandler;
import tiles.Tile;
import static gui.mainToolbox.MouseInterpreter.*;
import gui.pages.Page;
import items.Item;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.function.Predicate;
import testUtilities.TestUtil;


/**
 *
 * @author Adam Whittaker
 */
public abstract class Main extends Canvas implements Runnable, ActionListener, Page{
    
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
    private KeyProcessor keyProcessor = new KeyProcessor();
    protected Window window;
    protected Page page;
    public PageFlipper pageFlipper;

    public static final SoundHandler soundSystem = new SoundHandler();
    public Pacemaker pacemaker;
    public final static MiscAnimator animator = new MiscAnimator();
    protected static final GuiBase gui = new GuiBase();
    protected MouseInterpreter mouse = new MouseInterpreter();
    public volatile Area currentArea;
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
    public void setDialogue(Dialogue dialogue){
        gui.setDialogue(dialogue);
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
     * Sets the focus directly (top-left rather than center).
     * @param x The x pixel
     * @param y The y pixel
     */
    public void setDirectFocus(int x, int y){
        mouse.setDirectFocus(x, y);
    }
    
    public static void addMessage(Color col, String str){
        gui.addMessage(col, str);
    }
    
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
     * @param ae
     * @thread render
     */
    @Override
    public void actionPerformed(ActionEvent ae){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(2);
            return;
        }
        Graphics2D bsg = (Graphics2D) bs.getDrawGraphics();
        page.paint(bsg);
        bsg.dispose();
        bs.show();
    }
    
    @Override
    public void paint(Graphics2D bsg){
        BufferedImage buffer = new BufferedImage((int)(((double)WIDTH)/zoom), (int)(((double)HEIGHT)/zoom), BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        //@Unfinished
        TestUtil.setGraphics(g);
        
        g.setColor(Color.black);
        g.fillRect(0, 0, (int)(((double)WIDTH)/zoom), (int)(((double)HEIGHT)/zoom));
        paintArea(currentArea, g);
        currentArea.renderObjects(g, focusX, focusY);
        animator.animate(g, focusX, focusY);
        AffineTransform at = AffineTransform.getScaleInstance(zoom, zoom);
        bsg.drawRenderedImage(buffer, at);
        gui.paint(bsg);
        //@Unfinished
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
        pacemaker.stop();
        try{
            runThread.join();
            turnThread.join();
            running = false;
        }catch(InterruptedException e){
            e.printStackTrace(exceptionStream);
        }
    }
        
    /**
     * Paints the given area on the given graphics.
     * @thread render
     * @param area The area to paint.
     * @param g The graphics to paint on.
     */
    public void paintArea(Area area, Graphics g){
        g.setColor(ConstantFields.exploredColor);
        for(int y=focusY, maxY=focusY+area.dimension.height*16;y<maxY;y+=16){
            for(int x=focusX, maxX=focusX+area.dimension.width*16;x<maxX;x+=16){
                int tx = (x-focusX)/16, ty = (y-focusY)/16;
                try{
                    Image shade, exShade;
                    if(area.overlay.isUnexplored(tx, ty)) continue;
                    else shade = VisibilityOverlay.unexploredFog.getShadow(area.overlay.map, tx, ty, 0, true);
                    if(x<0||y<0||x*zoom>WIDTH||y*zoom>HEIGHT) continue;
                    Tile tile = area.map[ty][tx];
                    if(tile!=null) tile.paint(g, x, y);
                    
                    if(!area.overlay.isExplored(tx, ty))
                        exShade = VisibilityOverlay.exploredFog.getShadow(area.overlay.map, tx, ty, 1, false);
                    else exShade = VisibilityOverlay.exploredFog.getFullShader();
                    if(exShade!=null) g.drawImage(exShade, x, y, null);
                    if(shade!=null) g.drawImage(shade, x, y, null);
                }catch(ArrayIndexOutOfBoundsException e){/*Skip frame*/}
            }
        }
    }
    
    public void toggleInventory(){
        gui.toggleInventory();
    }
    
    public void setInventoryActive(boolean i){
        gui.setInventoryActive(i);
    }
    
    public void setInventoryActive(boolean i, Predicate<Item> pred){
        gui.setInventoryActive(i, pred);
    }
    
    protected final void resetGUIScreens(){
        gui.resetScreens();
    }
    
    @Override
    public void addKeyListener(KeyListener k){
        keyProcessor.listener = k;
    }
    
}
