
package gui;

import containers.Floor;
import exceptions.ReceptacleIndexOutOfBoundsException;
import exceptions.ReceptacleOverflowException;
import fileLogic.ReadWrite;
import items.Apparatus;
import items.Item;
import items.ItemBuilder;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import level.Area;
import level.Location;
import level.RoomBuilder;
import listeners.BuffEventInitiator;
import listeners.GrimReaper;
import logic.Distribution;
import logic.IDHandler;
import logic.ImageHandler;
import tiles.AnimatedTile;
import tiles.Tile;


/**
 *
 * @author Adam Whittaker
 */
public class MainClass extends Canvas implements ActionListener, Runnable{
    
    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    public static MessageQueue messageQueue = new MessageQueue();

    private Thread thread;
    private boolean running = false;

    private Handler handler;

    public static final IDHandler idhandler = new IDHandler(); //Creates UUIDs for GameObjects.
    public static final GrimReaper reaper = new GrimReaper(); //Handles death.
    public static final BuffEventInitiator buffinitiator = new BuffEventInitiator(); //Handles buffs.
    public static Area area1 = RoomBuilder.floodedVault(new Location("Test", "temporaryTiles"), ItemBuilder.amulet());
    public static long frameNumber = 0;
    private int framerate = 0;

    public MainClass(){
        ImageHandler.initializeMap();

        handler = new Handler();

        Window win = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
    }

    public static void main(String[] args){
        ReadWrite rw = new ReadWrite("filetesting/test.txt");
        rw.write("TEST STRING");
        System.out.println(rw.read());
        //MainClass mc = new MainClass();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void run(){
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
            if(running){
                render();
            }
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    public void tick(){
        handler.tick();
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(4);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);
        framerate++;
        if(framerate == 60){
            frameNumber = (frameNumber+1) % 1000000000;
            framerate = 0;
        }
        paintArea(area1, g);
        g.dispose();
        bs.show();
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
    
    public void paintArea(Area area, Graphics g){
        for(int y=0;y<area.dimension.height*16;y+=16){
            for(int x=0;x<area.dimension.width*16;x+=16){
                Tile tile = area.map[y/16][x/16];
                if(tile instanceof AnimatedTile)
                    g.drawImage(((AnimatedTile)tile)
                            .animation.frames
                            [(int)(frameNumber%(((AnimatedTile)tile).animation.frames.length))]
                            .getImage(),x,y,null);
                else g.drawImage(((ImageIcon)tile.getIcon()).getImage(),x,y,null);
                try{
                    if(tile.receptacle instanceof Floor&&!tile.receptacle.isEmpty())
                        g.drawImage(tile.receptacle.peek().icon,x,y,null);
                }catch(ReceptacleIndexOutOfBoundsException ignore){}
            }
        }
    }
}
