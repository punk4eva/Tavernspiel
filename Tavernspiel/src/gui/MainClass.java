
package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import level.Area;
import level.Location;
import listeners.BuffEventInitiator;
import listeners.GrimReaper;
import logic.IDHandler;
import logic.ImageHandler;


/**
 *
 * @author Adam Whittaker
 */
public class MainClass extends Canvas implements ActionListener, Runnable{
    
    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;

    private Handler handler;

    public static final IDHandler idhandler = new IDHandler(); //Creates UUIDs for GameObjects.
    public static final GrimReaper reaper = new GrimReaper(); //Handles death.
    public static final BuffEventInitiator buffinitiator = new BuffEventInitiator(); //Handles buffs.
    public static final Area area1 = new Area(new Dimension(280,48), new Location("Test","temporaryTiles"));

    public MainClass(){
        ImageHandler.initializeMap();

        handler = new Handler();

        Window win = new Window(WIDTH, HEIGHT, "Tavernspiel", this);
    }

    public static void main(String[] args){
        MainClass mc = new MainClass();
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
                //@charlie
                //Paint tile map[y/16][x/16] to canvas at coords x, y.
                //g.drawImage(ImageHandler.getImageIcon("shaderns", new Location("Test", "temporaryTiles")).getImage(),x,y,null);
                g.drawImage(ImageHandler.getImageIcon(area.map[y/16][x/16].name,new Location("Test","temporaryTiles" )).getImage(),x,y,null);
                
            }
        }
    }
}
