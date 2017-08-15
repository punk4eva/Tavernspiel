
package gui;

import animation.Animation;
import containers.Floor;
import containers.Receptacle;
import dialogues.Dialogue;
import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import fileLogic.ReadWrite;
import items.Apparatus;
import items.Item;
import items.ItemBuilder;
import items.equipment.Wand;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.ImageIcon;
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
public abstract class MainClass extends Canvas implements Runnable, MouseListener{
    
    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    public static MessageQueue messageQueue = new MessageQueue();
    protected final SoundHandler soundSystem = new SoundHandler();
    public PrintStream exceptionStream, performanceStream;

    private Thread thread;
    private boolean running = false;
    protected Window window;

    protected final Handler handler;

    public static final IDHandler idhandler = new IDHandler(); //Creates UUIDs for GameObjects.
    public static final GrimReaper reaper = new GrimReaper(); //Handles death.
    public static final BuffEventInitiator buffinitiator = new BuffEventInitiator(); //Handles buffs.
    public ArrayList<Screen> activeScreens = new ArrayList<>();
    private Dialogue currentDialogue = null; //null if no dialogue.
    public Area currentArea;
    public static long frameDivisor = 10000;
    public static long frameNumber = 0;

    public MainClass(){
        try{
            exceptionStream = new PrintStream(new File("log/exceptions.txt"));
            performanceStream = new PrintStream(new File("log/performance.txt"));
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }
        ImageHandler.initializeMap();

        handler = new Handler();
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
    
    public void addAnimation(Animation an){
        frameDivisor = Utils.frameUpdate(frameDivisor, an.frames.length);
    }

    @Override
    public void run(){
        addMouseListener(this);
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            for(double d = delta; d >= 1; d--){
                tick();
            }
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

    public void tick(){
        handler.tick();
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
        //window.frame.setLayout(new GridLayout(area.dimension.height, area.dimension.width, 0, 0));
        for(int y=0;y<area.dimension.height*16;y+=16){
            for(int x=0;x<area.dimension.width*16;x+=16){
                //window.frame.add(area.map[y/16][x/16]);
                
                Tile tile = area.map[y/16][x/16];
                if(tile instanceof AnimatedTile)
                    ((AnimatedTile) tile).animation.animate(g, x, y, frameNumber);
                else g.drawImage(tile.image,x,y,null);
                try{
                    Receptacle temp = area.getReceptacle(x, y);
                    if(temp instanceof Floor&&!temp.isEmpty())
                        g.drawImage(temp.peek().icon,x,y,null);
                }catch(ReceptacleIndexOutOfBoundsException ignore){}
                
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
    public void mouseReleased(MouseEvent me){/**Ignore*/}
    @Override
    public void mouseEntered(MouseEvent me){/**Ignore*/}
    @Override
    public void mouseExited(MouseEvent me){/**Ignore*/}
    
    public static int[] translateMouseCoords(double mx, double my){
        return new int[]{(int)Math.floor(mx/16), (int)Math.floor(my/16)};
    }
    
}
