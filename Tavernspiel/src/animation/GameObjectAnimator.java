
package animation;

import gui.mainToolbox.Main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import javax.swing.ImageIcon;
import listeners.AnimationListener;
import logic.Distribution;
import logic.ImageUtils;

/**
 *
 * @author Adam Whittaker
 * 
 * This class holds the animations that a GameObject will use.
 */
public class GameObjectAnimator implements AnimationListener, Serializable{
    
    private final static long serialVersionUID = -21002248;
    
    private final HashMap<String, Animation> map = new HashMap<>();
    public Animation active;
    public String currentName = "DEFAULT";
    private boolean waitingForDone = false;
    private transient CyclicBarrier barrier = new CyclicBarrier(2);
    
    /**
     * Creates a new instance of this class.
     * @param na The array of names of the animations.
     * @param ani The array of animations.
     */
    public GameObjectAnimator(String[] na, Animation[] ani){
        if(ani.length!=na.length) throw new IllegalArgumentException("Name length and animation length aren't the same!");
        for(int n=0;n<na.length;n++){
            ani[n].changeListener(this);
            map.put(na[n], ani[n]);
        }
        active = ani[0];
    }
    
    /**
     * Creates an instance of this class personalized for gases.
     * @param gasAnimation
     */
    public GameObjectAnimator(Animation gasAnimation){
        gasAnimation.changeListener(this);
        map.put("gas", gasAnimation);
        active = gasAnimation;
    }
    
    /**
     * Creates an instance of a still image.
     * @param icon
     */
    public GameObjectAnimator(ImageIcon icon){
        active = new StillAnimation(icon);
        map.put("default", active);
    }
    
    /**
     * Creates an instance.
     * @param spriteSheet The sprite sheet to copy.
     * @param nms The array of names of the Animations.
     * @param lengths The length of each Animation.
     */
    public GameObjectAnimator(BufferedImage spriteSheet, String[] nms, int[] lengths){
        List<ImageIcon> imgList = new LinkedList<>();
        for(int y=0, ych=spriteSheet.getHeight();y<ych;y+=16){
            for(int x=0, xch=spriteSheet.getWidth();x<xch;x+=16){
                imgList.add(new ImageIcon(spriteSheet.getSubimage(x, y, 16, 16)));
            }
        }
        int count = 0;
        for(int n=0;n<nms.length;n++){
            List<ImageIcon> sublist = imgList.subList(count, count+lengths[n]);
            count+=lengths[n];
            map.put(nms[n], new Animation(sublist.toArray(new ImageIcon[lengths[n]]), 500, this));
        }
        active = map.get("stand");
        map.put("stand", new RandomAnimation(active, new Distribution(7, 8)));
    }
    
    /**
     * Switches to a different animation.
     * @param name The name of the new animation.
     */
    public void switchTo(String name){
        active = map.get(name);
        currentName = name;
    }
    
    /**
     * Switches to a different animation and then back again after 1 cycle.
     * @param name The name of the new animation.
     */
    public synchronized void switchAndBack(String name){
        Animation current = active;
        active = map.get(name);
        waitingForDone = true;
        try{
            barrier.await();
        }catch(InterruptedException | BrokenBarrierException e){
            e.printStackTrace(Main.exceptionStream);
        }
        waitingForDone = false;
        active = current;
    }
    
    /**
     * Switches to a new animation and then fades away.
     * @param name The new animation.
     */
    public synchronized void switchFade(String name){
        active = map.get(name);
        Animation fade = getFadeAnimation(active.frames[active.frames.length-1]);
        waitingForDone = true;
        try{
            barrier.await();
        }catch(InterruptedException | BrokenBarrierException e){
            e.printStackTrace(Main.exceptionStream);
        }
        barrier.reset();
        active = fade;
    }

    @Override
    public synchronized void done(Animation a){
        if(waitingForDone){
            try{
                barrier.await();
            }catch(InterruptedException | BrokenBarrierException e){
                e.printStackTrace(Main.exceptionStream);
            }
            barrier.reset();
        }
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
    
    
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        barrier = new CyclicBarrier(2);
    }
    
}
