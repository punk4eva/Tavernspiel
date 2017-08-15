
package animation;

import gui.Handler;
import gui.MainClass;
import java.awt.Image;
import java.awt.image.BufferedImage;
import listeners.AnimationListener;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 */
public class GameObjectAnimator implements AnimationListener{
    
    private final String[] names;
    private final Animation[] animations;
    public Animation active;
    private boolean waitingForDone = false;
    
    public GameObjectAnimator(String[] na, Animation[] ani){
        if(ani.length!=na.length) throw new IllegalArgumentException("Name length and animation length aren't the same!");
        names = na;
        animations = ani;
        active = ani[0];
    }
    
    public GameObjectAnimator(Animation gasAnimation){
        animations = new Animation[]{gasAnimation};
        names = new String[]{"gas"};
        active = gasAnimation;
    }
    
    public void switchTo(String name){
        for(int n=0;n<names.length;n++){
            if(name.equals(names[n])){
                active = animations[n];
                break;
            }
        }
    }
    
    public synchronized void switchAndBack(String name){
        Animation current = active;
        for(int n=0;n<names.length;n++){
            if(name.equals(names[n])){
                active = animations[n];
                break;
            }
        }
        waitingForDone = true;
        try{
            wait();
        }catch(InterruptedException e){
            e.printStackTrace(MainClass.exceptionStream);
        }
        waitingForDone = false;
        active = current;
    }
    
    public synchronized void switchFade(String name){
        for(int n=0;n<names.length;n++){
            if(name.equals(names[n])){
                active = animations[n];
                break;
            }
        }
        Animation fade = getFadeAnimation(active.frames[active.frames.length-1]);
        waitingForDone = true;
        try{
            wait();
        }catch(InterruptedException e){
            e.printStackTrace(MainClass.exceptionStream);
        }
        active = fade;
        try{
            wait();
        }catch(InterruptedException e){
            e.printStackTrace(MainClass.exceptionStream);
        }
        waitingForDone = false;
    }

    @Override
    public synchronized void done(){
        if(waitingForDone) notify();
    }
    
    public Animation getFadeAnimation(Image img){
        BufferedImage bi = ImageUtils.addImageBuffer(img);
        Image[] ret = new Image[25];
        for(int n=0;n<25;n++){
            ret[n] = ImageUtils.fade(bi, 245-10*n);
        }
        return new Animation(ret, this); 
    }
    
}
