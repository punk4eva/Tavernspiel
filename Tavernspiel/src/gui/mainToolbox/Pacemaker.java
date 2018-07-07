
package gui.mainToolbox;

import javax.swing.Timer;

/**
 *
 * @author Adam Whittaker
 * 
 * This class wraps a timer in order to control frame rate internally.
 */
public class Pacemaker{
    
    private Timer timer;
    
    protected Pacemaker(Main main){
        timer = new Timer(25, main);
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
    }
    
}
