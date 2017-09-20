
package gui;

import dialogues.PauseMenu;
import animation.Animation;
import containers.Floor;
import containers.Receptacle;
import dialogues.Dialogue;
import items.equipment.Wand;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import level.Area;
import listeners.BuffEventInitiator;
import listeners.GrimReaper;
import logic.IDHandler;
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
public abstract class MainClass extends Canvas implements Runnable, MouseListener, MouseMotionListener, MouseWheelListener{
    
    public static final int WIDTH = 780, HEIGHT = WIDTH / 12 * 9;
    public static MessageQueue messageQueue = new MessageQueue();
    protected final SoundHandler soundSystem = new SoundHandler();
    public transient static PrintStream exceptionStream, performanceStream;

    private Thread thread;
    private boolean running = false;
    protected Window window;

    protected final Handler handler;

    public static final IDHandler idhandler = new IDHandler(); //Creates UUIDs for GameObjects.
    public static GrimReaper reaper; //Handles death.
    public static final BuffEventInitiator buffinitiator = new BuffEventInitiator(); //Handles buffs.
    private final ViewableList viewables = new ViewableList();
    private Dialogue currentDialogue = null; //null if no dialogue.
    public Area currentArea;
    private static int focusX=16, focusY=16;
    private int xOfDrag=-1, yOfDrag=-1;
    private String clickMode = "normal";
    private static double zoom = 1.0;
    public static final double MAX_ZOOM = 8.0, MIN_ZOOM = 0.512;
    public static long frameDivisor = 10000;
    public static long frameNumber = 0;
    public static double gameTurns = 0;
    
    private class ViewableList{
        
        private final List<Viewable> viewables = new LinkedList<>();
        final List<Screen> screens = new LinkedList<>();
        
        void removeTopViewable(){
            Viewable top = viewables.remove(viewables.size()-1);
            screens.removeAll(top.getScreenList());
        }
        
        void addViewable(Viewable viewable){
            screens.removeAll(viewables.get(viewables.size()-1).getScreenList());
            viewables.add(viewable);
            screens.addAll(viewable.getScreenList());
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

    /**
     * Creates a new instance.
     */
    public MainClass(){
        try{
            exceptionStream = new PrintStream(new File("log/exceptions.txt"));
            performanceStream = new PrintStream(new File("log/performance.txt"));
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }

        handler = new Handler();
        reaper = new GrimReaper(handler);
    }
    
    /**
     * Adds a Viewable to the display.
     * @param viewable
     */
    public void addViewable(Viewable viewable){
        viewables.addViewable(viewable);
    }
    
    /**
     * Removes the top Viewable.
     */
    public void removeTopViewable(){
        viewables.removeTopViewable();
    }
    
    /**
     * Gets the Handler
     * @return The Handler
     */
    public Handler getHandler(){
        return handler;
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
        Window.MusicVolume = newVolume;
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
     * Gets the zoom value.
     * @return The zoom value.
     */
    public static double getZoom(){
        return zoom;
    }
    
    /**
     * Returns the focus.
     * @return An array representing the x and y coordinates of focus.
     */
    public static int[] getFocus(){
        return new int[]{focusX, focusY};
    }

    @Override
    public void run(){
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        this.requestFocus();
        //long lastTime = System.nanoTime();
        //double amountOfTicks = 60.0;
        //double ns = 1000000000 / amountOfTicks;
        //double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            //long now = System.nanoTime();
            //delta += (now - lastTime) / ns;
            //lastTime = now;
            /*for(double d = delta; d >= 1; d--){
                tick();
            }*/
            //if(running){
                render(frames);
            //}
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                performanceStream.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    /**
     * Runs the game for the given amount of turns.
     * @param turnsConsumed The amount of turns to run.
     */
    public void turn(double turnsConsumed){
        gameTurns += turnsConsumed;
        double delta=0;
        for(double d=turnsConsumed;d>0;d-=d>=1 ? (delta=1) : (delta=d)){
            System.out.println("DELTA: " + delta);
            handler.turn(delta);
        }
    }

    /**
     * Renders the game with the given framerate.
     * @param frameInSec The framerate.
     */
    public void render(int frameInSec){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(4);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if(frameInSec%16==0){
            frameNumber = (frameNumber+1) % frameDivisor;
        }
        //pm.paint(g);
        paintArea(currentArea, g);
        handler.render(g);
        viewables.streamViewables().forEach(v -> {
            v.paint(g);
        });
        if(currentDialogue!=null) currentDialogue.paint(g);
        g.dispose();
        bs.show();
    }

    /**
     * Starts the game.
     */
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    /**
     * Stops the game.
     */
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace(exceptionStream);
        }
    }
        
    /**
     * Paints the given area on the given graphics.
     * @param area The area to paint.
     * @param g The graphics to paint on.
     */
    public void paintArea(Area area, Graphics g){
        for(int y=focusY;y<focusY+area.dimension.height*16;y+=16){
            for(int x=focusX;x<focusX+area.dimension.width*16;x+=16){
                
                Tile tile = area.map[(y-focusY)/16][(x-focusX)/16];
                int xz = (int)(x*zoom);
                int yz = (int)(y*zoom);
                if(tile instanceof AnimatedTile)
                    ((AnimatedTile) tile).animation.animate(g, xz, yz);
                else{
                    if(zoom!=1){
                        int l = (int)(16*zoom);
                        g.drawImage(tile.image.getImage().getScaledInstance(l, l, 0),xz,yz,null);
                    }else g.drawImage(tile.image.getImage(),xz,yz,null);
                }
                Receptacle temp = area.getReceptacle(x, y);
                if(temp instanceof Floor&&!temp.isEmpty()) temp.peek().animation.animate(g, x, y);
            }
        }
    }
    
    /**
     * Draws a wand arc from the given wand using the given coordinates.
     * @param wand The wand to draw.
     * @param x The starting x coordinate.
     * @param y The starting y coordinate.
     * @param destx The destination x coordinate.
     * @param desty The destination y coordinate.
     */
    @Unfinished
    public void drawWandArc(Wand wand, int x, int y, int destx, int desty){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Displays a searching animation.
     * @param ary The list of points that was searched.
     * @param searchSuccessful Whether the search was successful.
     */
    public void searchAnimation(List<Point> ary, boolean searchSuccessful){
        if(searchSuccessful) soundSystem.playSFX("Misc/mystery.wav");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseClicked(MouseEvent me){
        if(viewables.screens.isEmpty()){
            int[] p = translateMouseCoords(me.getX(), me.getY());
            
        }else{
            boolean notClicked = true;
            for(Screen sc : viewables.screens){ //Used for-each instead of stream because of "break".
                if(sc.withinBounds(me.getX(), me.getY())){
                    if(!sc.name.equals("blank click")){
                        sc.wasClicked(me);
                        notClicked = false;
                    }
                    break;
                }
            }
            if(notClicked) currentDialogue.clickedOff();
        }
    }
    @Override
    public void mousePressed(MouseEvent me){/**Ignore*/}
    @Override
    public void mouseReleased(MouseEvent me){
        if(xOfDrag!=-1){
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
    public static int[] translateMouseCoords(double mx, double my){
        return new int[]{(int)Math.floor(mx/16), (int)Math.floor(my/16)};
    }

    @Override
    public void mouseDragged(MouseEvent me){
        if(currentDialogue == null && viewables.viewables.size() <= 1){
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
            case -1: if(zoom<MAX_ZOOM) zoom *= 1.25;
                break;
            default: if(zoom>MIN_ZOOM) zoom /= 1.25;
        }
    }
    
}
