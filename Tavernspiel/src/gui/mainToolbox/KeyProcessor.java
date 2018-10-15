
package gui.mainToolbox;

import gui.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author Adam Whittaker
 */
public class KeyProcessor extends Thread implements KeyListener{
    
    private final LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();
    protected KeyListener listener;
    
    public KeyProcessor(){
        super("Key Processor");
    }
    
    
    @Override
    public void run(){
        while(Window.main.running){
            try{
                queue.take().run();
            }catch(InterruptedException ex){}
        }
    }

    @Override
    public void keyTyped(KeyEvent ke){
        queue.add(() -> listener.keyTyped(ke));
    }

    @Override
    public void keyPressed(KeyEvent ke){
        queue.add(() -> listener.keyPressed(ke));
    }

    @Override
    public void keyReleased(KeyEvent ke){
        queue.add(() -> listener.keyReleased(ke));
    }
    
}
