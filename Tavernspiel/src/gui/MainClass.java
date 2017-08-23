
package gui;

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
import java.util.ArrayList;
import level.Area;
import listeners.BuffEventInitiator;
import listeners.GrimReaper;
import logic.IDHandler;
import logic.ImageHandler;
import logic.SoundHandler;
import logic.Utils;
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
    public static PrintStream exceptionStream, performanceStream;

    private Thread thread;
    private boolean running = false;
    protected Window window;

    protected final Handler handler;

    public static final IDHandler idhandler = new IDHandler(); //Creates UUIDs for GameObjects.
    public static final GrimReaper reaper = new GrimReaper(); //Handles death.
    public static final BuffEventInitiator buffinitiator = new BuffEventInitiator(); //Handles buffs.
    public ArrayList<Screen> activeScreens = new ArrayList<>();
    public ArrayList<Viewable> activeViewables = new ArrayList<>();
    private Dialogue currentDialogue = null; //null if no dialogue.
    public Area currentArea;
    private int focusX=16, focusY=16;
    private int xOfDrag=-1, yOfDrag=-1;
    private String clickMode = "normal";
    private static double zoom = 1.0;
    public static final double MAX_ZOOM = 8.0;
    public static final double MIN_ZOOM = 0.512;
    public static long frameDivisor = 10000;
    public static long frameNumber = 0;
    public static double gameTurns = 0;

    public MainClass(){
        try{
            exceptionStream = new PrintStream(new File("log/exceptions.txt"));
            performanceStream = new PrintStream(new File("log/performance.txt"));
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }
        ImageHandler.initializeMap();

        handler = new Handler(reaper);
    }
    
    public void addViewable(Viewable viewable){
        activeScreens.removeAll(activeViewables.get(activeViewables.size()-1).getScreenList());
        activeViewables.add(viewable);
        activeScreens.addAll(viewable.getScreenList());
    }
    
    public void removeTopViewable(){
        Viewable top = activeViewables.remove(activeViewables.size()-1);
        activeScreens.removeAll(top.getScreenList());
    }
    
    public void changeSFXVolume(float newVolume){
        Window.SFXVolume = newVolume;
    }
    
    public void changeMusicVolume(float newVolume){
        Window.MusicVolume = newVolume;
    }
    
    public void changeCurrentDialogue(Dialogue dialogue){
        currentDialogue = dialogue;
    }
    
    public void addScreen(Screen sc){
        activeScreens.add(sc);
    }
    
    public void removeScreens(Screen[] scs){
        for(Screen sc : scs){
            activeScreens.remove(sc);
        }
    }
    
    public static void addAnimation(Animation an){
        if(frameDivisor%an.frames.length!=0) 
            frameDivisor = Utils.frameUpdate(frameDivisor, an.frames.length);
    }
    
    public static double getZoom(){
        return zoom;
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

    /*public void tick(){
        handler.tick();
    }*/
    
    public void turn(double turnsConsumed){
        gameTurns += turnsConsumed;
        double delta=0;
        for(double d=turnsConsumed;d>0;d-=d>=1 ? (delta=1) : (delta=d)){
            System.out.println("DELTA: " + delta);
            handler.turn(delta);
        }
    }

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
        handler.render(g);
        paintArea(currentArea, g);
        activeViewables.stream().forEach(v -> {
            v.paint(g);
        });
        if(currentDialogue!=null) currentDialogue.paint(g);
        g.dispose();
        bs.show();
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        System.setOut(performanceStream);
        performanceStream.flush();
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace(exceptionStream);
        }
    }
        
    
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
                        g.drawImage(tile.image.getScaledInstance(l, l, 0),xz,yz,null);
                    }else g.drawImage(tile.image,xz,yz,null);
                }
                Receptacle temp = area.getReceptacle(x, y);
                if(temp instanceof Floor&&!temp.isEmpty())
                    g.drawImage(temp.peek().icon,x,y,null);
            }
        }
    }
    
    public void drawWandArc(Wand wand, int x, int y, int destx, int desty){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void endGame(){
        stop();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void search(ArrayList<Point> ary, Area area, boolean searchSuccessful){
        if(searchSuccessful) soundSystem.playSFX("Misc/mystery.wav");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseClicked(MouseEvent me){
        if(activeScreens.isEmpty()){
            int[] p = translateMouseCoords(me.getX(), me.getY());
            
        }else{
            boolean notClicked = true;
            for(Screen sc : activeScreens){
                if(sc.withinBounds(me.getX(), me.getY())){
                    if(!sc.name.equals("blank click")) sc.wasClicked();
                    notClicked = false;
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
    
    public static int[] translateMouseCoords(double mx, double my){
        return new int[]{(int)Math.floor(mx/16), (int)Math.floor(my/16)};
    }

    @Override
    public void mouseDragged(MouseEvent me){
        if(currentDialogue == null && activeViewables.size() <= 1){
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
