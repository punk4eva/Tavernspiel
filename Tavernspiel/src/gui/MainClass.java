
package gui;

import ai.PlayerAI;
import animation.Animation;
import animation.StaticAnimator;
import containers.Floor;
import containers.Receptacle;
import creatureLogic.VisibilityOverlay;
import creatures.Hero;
import dialogues.Dialogue;
import dialogues.StatisticsDialogue;
import items.equipment.Wand;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;
import level.Area;
import listeners.AnimationListener;
import logic.ConstantFields;
import logic.SoundHandler;
import logic.Utils;
import logic.Utils.Unfinished;
import pathfinding.Point;
import tiles.AnimatedTile;
import tiles.Tile;


/**
 *
 * @author Adam Whittaker
 */
public abstract class MainClass extends Canvas implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener, AnimationListener{
    
    public static final int WIDTH = 780, HEIGHT = WIDTH / 12 * 9;
    public static MessageQueue messageQueue = new MessageQueue();
    public final SoundHandler soundSystem = new SoundHandler();
    public transient static PrintStream exceptionStream, performanceStream;

    private volatile boolean running = false;
    private Thread renderThread;
    public TurnThread turnThread = new TurnThread();
    protected Window window;
    protected HUD hud;

    private final ViewableList viewables = new ViewableList();
    protected volatile Dialogue currentDialogue = null; //null if no dialogue.
    public volatile Area currentArea;
    public volatile Hero player;
    protected volatile static int focusX=16, focusY=16;
    private int xOfDrag=-1, yOfDrag=-1;
    private static double zoom = 1.0;
    public static final double MAX_ZOOM = 8.0, MIN_ZOOM = 0.512;
    public static long frameDivisor = 10000;
    public static long frameNumber = 0;
    public static double gameTurns = 0;
    
    private class ViewableList{
        
        private final List<Viewable> viewables = new LinkedList<>();
        final List<Screen> screens = new LinkedList<>();
        final List<Screen> draggables = new LinkedList<>();
        private Screen lastDragged;
        
        void removeTopViewable(){
            Viewable top = viewables.remove(viewables.size()-1);
            screens.removeAll(top.getScreens());
        }
        
        void addViewable(Viewable viewable){
            if(!viewables.isEmpty())screens.removeAll(viewables.get(viewables.size()-1).getScreens());
            viewables.add(viewable);
            screens.addAll(viewable.getScreens());
        }
        
        void addDraggable(Screen lst){
            if(draggables.isEmpty()) lastDragged = lst;
            draggables.add(lst);
        }
        
        boolean viewablesEmpty(){
            return viewables.isEmpty();
        }
        
        boolean screensEmpty(){
            return screens.isEmpty();
        }
        
        Stream<Viewable> streamViewables(){
            return viewables.stream();
        }
        
        Stream<Screen> streamScreens(){
            return screens.stream();
        }
        
        int viewablesSize(){
            return viewables.size();
        }
        
        int screensSize(){
            return screens.size();
        }
        
    }
    
    public class TurnThread extends Thread{
        
        public final LinkedBlockingQueue<Runnable> queuedEvents = new LinkedBlockingQueue<>();
        
        TurnThread(){
            super("Turn Thread");
        }
        
        @Override
        public synchronized void run(){
            while(running){
                try{
                    queuedEvents.take().run();
                }catch(InterruptedException e){}
                while(((PlayerAI)player.attributes.ai).unfinished){
                    turn(player.attributes.speed);
                }
            }
        }
        
        public void click(int x, int y){
            if(player.x!=x||player.y!=y) queuedEvents.add(() -> {
                ((PlayerAI)player.attributes.ai).unfinished = true;
                player.attributes.ai.setDestination(x, y);
            });
            else queuedEvents.add(() -> {
                try{
                    player.attributes.ai.BASEACTIONS.pickUp(player);
                }catch(NullPointerException e){
                    new StatisticsDialogue(player).next();
                }
            });
        }
        
        /**
         * Runs the game for the given amount of turns.
         * @param delta The amount of turns to run.
         */
        private void turn(double delta){
            gameTurns += delta;
            for(;delta>=1;delta--) currentArea.turn(1.0);
            if(delta!=0) currentArea.turn(delta);
        }
        
    }

    /**
     * Creates a new instance.
     * @thread progenitor
     */
    public MainClass(){
        try{
            exceptionStream = new PrintStream(new File("log/exceptions.txt"));
            performanceStream = new PrintStream(new File("log/performance.txt"));
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }
    }
    
    public void addEvent(Runnable r){
        turnThread.queuedEvents.add(r);
    }
    
    /**
     * Adds a Viewable to the display.
     * @param viewable
     */
    public void addViewable(Viewable viewable){
        viewables.addViewable(viewable);
    }
    
    /**
     * Adds a Viewable to the display.
     * @param scs
     */
    public void addDraggable(Screen scs){
        viewables.addDraggable(scs);
    }
    
    /**
     * Removes the top Viewable.
     */
    public void removeTopViewable(){
        viewables.removeTopViewable();
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
    public void changeCurrentDialogue(Dialogue dialogue){
        currentDialogue = dialogue;
    }
    
    /**
     * Adds a screen to the top of the Viewable.
     * @param sc
     */
    public void addScreen(Screen sc){
        viewables.screens.add(sc);
    }
    
    /**
     * Removes the array of Screens from the active list.
     * @param scs The regex.
     */
    public void removeScreens(Screen[] scs){
        for(Screen sc : scs){
            viewables.screens.remove(sc);
        }
    }
    
    /**
     * Registers an Animation.
     * @param an The Animation to register.
     */
    public static void addAnimation(Animation an){
        if(frameDivisor%an.frames.length!=0) 
            frameDivisor = Utils.frameUpdate(frameDivisor, an.frames.length);
    }
    
    /**
     * Returns the focus.
     * @return An array representing the x and y coordinates of focus.
     */
    public static int[] getFocus(){
        return new int[]{focusX, focusY};
    }
    
    /**
     * Sets the focus based on the tile coordinates.
     * @param tilex
     * @param tiley
     */
    public void setFocus(int tilex, int tiley){
        int z = (int)(8*zoom);
        focusX = WIDTH/2 - z - tilex * 16;
        focusY = HEIGHT/2 - z - tiley * 16;
    }

    /**
     * @thread render
     */
    @Override
    public void run(){
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        this.requestFocus();
        long time = System.currentTimeMillis();
        int frames = 0;
        while(running){
            render(frames);
            frames++;
            if(System.currentTimeMillis() - time > 1000){
                time += 1000;
                performanceStream.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    /**
     * Renders the game with the given framerate.
     * @thread render
     * @param frameInSec The framerate.
     */
    public void render(int frameInSec){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(2);
            return;
        }
        Graphics2D bsg = (Graphics2D) bs.getDrawGraphics();
        BufferedImage buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        bsg.setColor(Color.black);
        bsg.fillRect(0, 0, WIDTH, HEIGHT);
        if(frameInSec%16==0){
            frameNumber = (frameNumber+1) % frameDivisor;
        }
        paintArea(currentArea, g);
        currentArea.renderObjects(g, focusX, focusY);
        if(StaticAnimator.current!=null) StaticAnimator.current.animate(g, focusX, focusY);
        AffineTransform at = AffineTransform.getScaleInstance(zoom, zoom);
        //at.concatenate(AffineTransform.getTranslateInstance(zoom*-20, zoom*-20));
        bsg.drawRenderedImage(buffer, at);
        messageQueue.paint(bsg);
        viewables.streamViewables().forEach(v -> {
            v.paint(bsg);
        });
        if(currentDialogue!=null) currentDialogue.paint(bsg);
        
        g.dispose();
        bsg.dispose();
        bs.show();
    }
    
    /**
     * Starts the game.
     */
    public synchronized void start(){
        renderThread = new Thread(this, "Render Thread");
        renderThread.setDaemon(true);
        turnThread.setDaemon(true);
        renderThread.start();
        turnThread.start();
        running = true;
    }

    /**
     * Stops the game.
     */
    public synchronized void stop(){
        try{
            renderThread.join();
            turnThread.join();
            running = false;
        }catch(Exception e){
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
                Optional<Image> shade = null;
                if(area.overlay.isExplored(tx, ty)) shade = Optional.of(VisibilityOverlay.exploredFog.getShadow(area.overlay.map, tx, ty, 1, false));
                else if(area.overlay.isUnexplored(tx, ty)) shade = Optional.ofNullable(VisibilityOverlay.unexploredFog.getShadow(area.overlay.map, tx, ty, 0, true));
                try{
                    if((shade!=null&&!shade.isPresent())||x<0||y<0||x*zoom>WIDTH||y*zoom>HEIGHT) continue;
                    Tile tile = area.map[ty][tx];
                    if(tile==null){
                    }else if(tile instanceof AnimatedTile)
                        ((AnimatedTile) tile).animation.animate(g, x, y);
                    else g.drawImage(tile.image.getImage(), x, y, null);
                    if(shade!=null) g.drawImage(shade.get(), x, y, null);
                }catch(ArrayIndexOutOfBoundsException e){/*skip frame*/}
            }
        }
    }

    @Override
    public void done(){
        StaticAnimator.complete();
    }
    
    @Override
    public void mouseClicked(MouseEvent me){
        boolean notClicked = true;
        int x = me.getX(), y = me.getY();
        for(Screen sc : viewables.screens){ //Used for-each instead of stream because of "break".
            if(sc.withinBounds(x, y)){
                if(!sc.name.equals("blank click")){
                    sc.wasClicked(me);
                    notClicked = false;
                }
                break;
            }
        }
        if(viewables.viewablesSize()==1){
            Integer[] p = translateMouseCoords(x, y);
            if(currentArea.tileFree(p[0], p[1])) turnThread.click(p[0], p[1]);
        }else if(notClicked){
            currentDialogue.clickedOff();
        }
    }
    @Override
    public void mousePressed(MouseEvent me){/**Ignore*/}
    @Override
    public void mouseReleased(MouseEvent me){
        if(!viewables.draggables.isEmpty()){
            int x = me.getX(), y = me.getY();
            if(viewables.lastDragged.withinBounds(x,y)) viewables.lastDragged.wasClicked(me);
            else for(Screen sc : viewables.draggables){
                if(sc.withinBounds(x, y)){
                    viewables.lastDragged = sc;
                    sc.wasClicked(me);
                    break;
                }
            }
        }else if(xOfDrag!=-1){
            xOfDrag = -1;
        }
    }
    @Override
    public void mouseEntered(MouseEvent me){/**Ignore*/}
    @Override
    public void mouseExited(MouseEvent me){/**Ignore*/}
    
    /**
     * Translates on screen pixel click coordinates to tile coordinates.
     * @param mx The x coordinate of the click.
     * @param my The y coordinate of the click.
     * @return An int array representing the tile coordinates.
     */
    public static Integer[] translateMouseCoords(double mx, double my){
        return new Integer[]{(int)Math.floor((mx-focusX)/16), (int)Math.floor((my-focusY)/16)};
    }

    @Override
    public void mouseDragged(MouseEvent me){
        if(!viewables.draggables.isEmpty()){
            int x = me.getX(), y = me.getY();
            if(viewables.lastDragged.withinBounds(x,y)) viewables.lastDragged.wasClicked(me);
            else for(Screen sc : viewables.draggables){
                if(sc.withinBounds(x, y)){
                    viewables.lastDragged = sc;
                    sc.wasClicked(me);
                    break;
                }
            }
        }else if(currentDialogue == null && viewables.viewables.size() <= 1){
            if(xOfDrag == -1){
                xOfDrag = me.getX() - focusX;
                yOfDrag = me.getY() - focusY;        
            }
            focusX = me.getX() - xOfDrag;
            focusY = me.getY() - yOfDrag;
        }
    }
    @Override
    public void mouseMoved(MouseEvent me){/**Ignore*/}
    @Override
    public void mouseWheelMoved(MouseWheelEvent me){
        //zoomCenterX = me.getX();
        //zoomCenterY = me.getY();
        switch(me.getWheelRotation()){
            case -1: if(zoom<MAX_ZOOM){
                zoom *= 1.25;
                performanceStream.println("ZOOM: " + zoom);
            }
                break;
            default: if(zoom>MIN_ZOOM){
                zoom /= 1.25;
                performanceStream.println("ZOOM: " + zoom);
            }
        }
    }
    
}
