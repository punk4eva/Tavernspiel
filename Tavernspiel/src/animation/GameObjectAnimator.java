
package animation;

import creatures.Creature;
import gui.MainClass;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import listeners.AnimationListener;
import listeners.DeathEvent;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 * 
 * This class holds the animations that a GameObject will use.
 */
public class GameObjectAnimator implements AnimationListener, Serializable{
    
    private final static long serialVersionUID = -21002248;
    
    private final String[] names;
    private final Animation[] animations;
    public Animation active;
    private boolean waitingForDone = false;
    
    /**
     * Creates a new instance of this class.
     * @param na The array of names of the animations.
     * @param ani The array of animations.
     */
    public GameObjectAnimator(String[] na, Animation[] ani){
        if(ani.length!=na.length) throw new IllegalArgumentException("Name length and animation length aren't the same!");
        names = na;
        animations = ani;
        for(Animation an : animations) an.changeListener(this);
        active = ani[0];
    }
    
    /**
     * Creates an instance of this class personalized for gases.
     * @param gasAnimation
     */
    public GameObjectAnimator(Animation gasAnimation){
        gasAnimation.changeListener(this);
        animations = new Animation[]{gasAnimation};
        names = new String[]{"gas"};
        active = gasAnimation;
    }
    
    /**
     * Creates an instance of a still image.
     * @param icon
     */
    public GameObjectAnimator(ImageIcon icon){
        names = new String[]{"default"};
        animations = new Animation[]{new StillAnimation(icon)};
        active = animations[0];
    }
    
    /**
     * Creates an instance.
     * @param spriteSheet The sprite sheet to copy.
     * @param nms The array of names of the Animations.
     * @param lengths The length of each Animation.
     */
    public GameObjectAnimator(BufferedImage spriteSheet, String[] nms, int[] lengths){
        names = nms;
        animations = new Animation[nms.length];
        List<ImageIcon> imgList = new LinkedList<>();
        for(int y=0, ych=spriteSheet.getHeight();y<ych;y+=16){
            for(int x=0, xch=spriteSheet.getWidth();x<xch;x+=16){
                imgList.add(new ImageIcon(spriteSheet.getSubimage(x, y, 16, 16)));
            }
        }
        int count = 0;
        for(int n=0;n<animations.length;n++){
            List<ImageIcon> sublist = imgList.subList(count, count+lengths[n]);
            count+=lengths[n];
            animations[n] = new Animation(sublist.toArray(new ImageIcon[lengths[n]]), 170, this);
        }
        for(int n=0;n<names.length;n++){
            if(names[n].equals("stand")){
                animations[n] = new RandomAnimation(animations[n], 30);
                break;
            }
        }
        active = animations[0];
    }
    
    /**
     * Switches to a different animation.
     * @param name The name of the new animation.
     */
    public void switchTo(String name){
        for(int n=0;n<names.length;n++){
            if(name.equals(names[n])){
                active = animations[n];
                active.offset = MainClass.frameNumber%active.frames.length;
                break;
            }
        }
    }
    
    /**
     * Switches to a different animation and then back again after 1 cycle.
     * @param name The name of the new animation.
     */
    public synchronized void switchAndBack(String name){
        Animation current = active;
        for(int n=0;n<names.length;n++){
            if(name.equals(names[n])){
                active = animations[n];
                active.offset = MainClass.frameNumber%active.frames.length;
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
        active.offset = MainClass.frameNumber%active.frames.length;
    }
    
    /**
     * Switches to a new animation and then fades away.
     * @param name The new animation.
     */
    public synchronized void switchFade(String name){
        for(int n=0;n<names.length;n++){
            if(name.equals(names[n])){
                active = animations[n];
                active.offset = MainClass.frameNumber%active.frames.length;
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
        active.offset = MainClass.frameNumber%active.frames.length;
    }
    
    /**
     * Kills the owner of this animation.
     * @param c The owner.
     */
    public synchronized void switchFadeKill(Creature c){
        switchFade("die");
        waitingForDone = true;
        try{
            wait();
        }catch(InterruptedException e){
            e.printStackTrace(MainClass.exceptionStream);
        }
        new DeathEvent(c, c.x, c.y, c.area).notifyEvent();
    }

    @Override
    public synchronized void done(){
        if(waitingForDone) notify();
    }
    
    /**
     * Gets a fade-away animation of a given image.
     * @param img The image to fade.
     * @return The animation.
     */
    public Animation getFadeAnimation(ImageIcon img){
        BufferedImage bi = ImageUtils.addImageBuffer(img);
        ImageIcon[] ret = new ImageIcon[25];
        for(int n=0;n<25;n++){
            ret[n] = new ImageIcon(ImageUtils.fade(bi, 245-10*n));
        }
        return new Animation(ret, this); 
    }
    
    public void animate(Graphics g, int x, int y){
        active.animate(g, x, y);
    }
    
}
