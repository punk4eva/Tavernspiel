
package animation;

import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;
import level.Location;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class Animation{
    
    public int fps = 60;
    protected ImageIcon[] frames;
    protected Timer timer;
    public int currentFrame = 0;
    
    public Animation(ImageIcon[] f){
        frames = f;
    }
    
    public void animate(JComponent jc, Graphics g){
        if(frames[currentFrame].getImageLoadStatus() == MediaTracker.COMPLETE){
            frames[currentFrame].paintIcon(jc, g, 0, 0);
            currentFrame = (currentFrame + 1) % frames.length;
        }
    }
    
    public void start(ActionListener al){
        if(timer == null){
            currentFrame = 0;
            timer = new Timer(1000/fps, al);
            timer.start();
        }else if(!timer.isRunning()){
            timer.restart();
        }
    }
    
    public void lapAndFade(ActionListener al){
        new Thread(() -> {
            start(al);
            try{
                Thread.sleep(1000 * frames.length / fps);
            }catch(InterruptedException ex){
                System.err.println("Thread interrupted.");
            }
            stop();
            fade();
        }).start();
    }
    
    /**
     * @unfinished
     */
    public void fade(){
        //unfinished
    }

    public void stop(){
        timer.stop();
    }
    
    public void addShaders(String shaderString, Location loc){
        if(shaderString.equals("well") || shaderString.equals("alchemypot")){
            ImageIcon shader = ImageHandler.getImageIcon(shaderString, loc);
            for(ImageIcon frame : frames){
                frame = ImageHandler.combineIcons(frame, shader);
            }
        }else{
            ImageIcon shader = ImageHandler.getImageIcon("shader" + shaderString, loc);
            for(ImageIcon frame : frames){
                frame = ImageHandler.combineIcons(frame, shader);
            }
        }
    }
    
}
