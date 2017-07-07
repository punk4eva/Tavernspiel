
package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import logic.SoundHandler;

/**
 *
 * @author Adam Whittaker
 */
public class MainClass extends Canvas implements ActionListener, Runnable{
    
    public static final int WIDTH = 640, HEIGHT = WIDTH /12*9;

    private Thread thread;
    private boolean running = false;
	
    private Handler handler;
	
    public MainClass(){
	
        handler = new Handler();
		
        new Window(WIDTH, HEIGHT, "Tavernspiel", this);
    }
    
    
    public static void main(String[] args){
        SoundHandler.addSong("Journey Through The Woods Part 1.wav");
        SoundHandler.addSong("Journey Through The Woods Part 2.wav");
        SoundHandler.addSong("Journey Through The Woods Part 3.wav");
        SoundHandler.playAbruptQueue(0);
    }
    

    
    public void actionPerformed(ActionEvent e){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running)
				render();
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
}
