
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
import java.util.Optional;
import level.Area;
import logic.ConstantFields;
import logic.SoundHandler;
import tiles.AnimatedTile;
import tiles.Tile;
import static gui.mainToolbox.MouseInterpreter.*;
import gui.pages.Page;
import items.Item;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Predicate;
import testUtilities.TestUtil;


/**
 *
 * @author Adam Whittaker
 */
public abstract class Main extends Canvas implements Runnable, ActionListener, Page{
    
    public static final int WIDTH = 780, HEIGHT = WIDTH / 12 * 9;
    public transient static PrintStream exceptionStream, performanceStream;

    protected volatile boolean running = false;
    private Thread runThread;
    public TurnThread turnThread = new TurnThread();
    protected Window window;
    protected Page page;
    public final PageFlipper pageFlipper;

    public final SoundHandler soundSystem = new SoundHandler();
    public final Pacemaker pacemaker;
    public final static MiscAnimator animator = new MiscAnimator();
    protected static final GuiBase gui = new GuiBase();
    protected MouseInterpreter mouse = new MouseInterpreter();
    public volatile Area currentArea;
    public volatile Hero player;


    /**
     * Creates a new instance.
     * @thread progenitor
     */
    public Main(){
        pageFlipper = new PageFlipper(this);
        pageFlipper.setPage("main");
        pacemaker = new Pacemaker(this);
        try{
            exceptionStream = new PrintStream(new File("log/exceptions.txt"));
            performanceStream = new PrintStream(new File("log/performance.txt"));
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }
    }
    
    /**
     * Adds an event to the event queue.
     * @param r
     */
    public void addEvent(Runnable r){
        turnThread.queuedEvents.add(r);
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
        Window.SFXVolume = newVolume;
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
    
    public static void addMessage(String col, String str){
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
    public final synchronized void start(){
        runThread = new Thread(this, "Run Thread");
        runThread.setDaemon(true);
        turnThread.setDaemon(true);
        runThread.start();
        turnThread.start();
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
                    Optional<Image> shade = null;
                    if(area.overlay.isExplored(tx, ty)) shade = Optional.of(VisibilityOverlay.exploredFog.getShadow(area.overlay.map, tx, ty, 1, false));
                    else if(area.overlay.isUnexplored(tx, ty)) shade = Optional.ofNullable(VisibilityOverlay.unexploredFog.getShadow(area.overlay.map, tx, ty, 0, true));
                    if((shade!=null&&!shade.isPresent())||x<0||y<0||x*zoom>WIDTH||y*zoom>HEIGHT) continue;
                    Tile tile = area.map[ty][tx];
                    if(tile==null){
                    }else if(tile instanceof AnimatedTile)
                        ((AnimatedTile) tile).animation.animate(g, x, y);
                    else g.drawImage(tile.image.getImage(), x, y, null);
                    if(shade!=null) g.drawImage(shade.get(), x, y, null);
                }catch(ArrayIndexOutOfBoundsException e){/*Skip frame*/}
            }
        }
    }
    
    public void toggleInventory(){
        gui.toggleInventory();
    }
    
    public void setInventoryActive(boolean i, boolean e){
        gui.setInventoryActive(i, e);
    }
    
    public void setInventoryActive(boolean i, boolean e, Predicate<Item> pred){
        gui.setInventoryActive(i, e, pred);
    }
    
    protected final void resetGUIScreens(){
        gui.resetScreens();
    }
    
}
