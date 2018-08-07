
package gui.mainToolbox;

import animation.TickedAnimation;
import java.util.LinkedList;
import javax.swing.Timer;

/**
 *
 * @author Adam Whittaker
 * 
 * This class wraps a timer in order to control frame rate internally.
 */
public class Pacemaker{
    
    private Timer timer;
    
    /**
     * Creates a new instance.
     * @param main The Main class
     */
    public Pacemaker(Main main){
        timer = new Timer(25, main);
        updateDelay(25);
    }
    
    public void start(){
        timer.start();
    }
    
    public void stop(){
        timer.stop();
    }
    
    public int getDelay(){
        return timer.getDelay();
    }
    
    public void setDelay(int d){
        timer.setDelay(d);
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
    
}
