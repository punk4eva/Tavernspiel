
package gui.mainToolbox;

import animation.TickedAnimation;
import java.util.LinkedList;

/**
 *
 * @author Adam Whittaker
 * 
 * This class wraps a timer in order to control frame rate internally.
 */
public class Pacemaker implements Runnable{
    
    private final Main main;
    private final Thread renderThread;
    
    private long now, delay, misses;
    private final int buffer = 4;
    
    /**
     * Creates a new instance.
     * @param m The Main.
     */
    public Pacemaker(Main m){
        main = m;
        renderThread = new Thread(this, "Render Thread");
        setDelay(1000L/60L);
    }
    
    public void start(){
        renderThread.start();
    }
    
    public void stop() throws InterruptedException{
        renderThread.join();
    }
    
    public long getDelay(){
        return delay;
    }
    
    public final void setDelay(long d){
        delay = d;
        updateDelay(d);
    }
    
    private void updateDelay(double d){
        animationsToInit.forEach((a) -> {
            a.setTicksPerFrame(d);
        });
    }
    
    /**
     * Queues an Animation to be fully initialized once a Pacemaker is created.
     * @param a
     */
    public static void registerWaitingAnimation(TickedAnimation a){
        animationsToInit.add(a);
    }
    
    private final static LinkedList<TickedAnimation> animationsToInit = new LinkedList<>();

    @Override
    public void run(){
        long n;
        main.createBufferStrategy(buffer);
        while(main.running){
            n = System.currentTimeMillis();
            if(n-now>delay){
                now = n;
                main.render();
            }else if(n-now>2L*delay){
                misses++;
                System.out.println("Miss: " + misses);
            }
        }
    }
    
}
