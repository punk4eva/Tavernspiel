
package animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
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
public class CreatureAnimator implements AnimationListener, GameObjectAnimator{
    
    private final static long serialVersionUID = -21002248;
    
    private final HashMap<String, FramedAnimation> map = new HashMap<>();
    private transient HashMap<String, FramedAnimation> reverse;
    public FramedAnimation active;
    public String currentName = "DEFAULT";
    private String nextName;
    private boolean facingLeft = true;
    
    /**
     * Creates a new instance of this class.
     * @param na The array of names of the animations.
     * @param ani The array of animations.
     */
    public CreatureAnimator(String[] na, FramedAnimation[] ani){
        if(ani.length!=na.length) throw new IllegalArgumentException("Name length and animation length aren't the same!");
        for(int n=0;n<na.length;n++){
            ani[n].changeListener(this);
            map.put(na[n], ani[n]);
        }
        active = ani[0];
        initializeReverse();
    }
    
    /**
     * Creates an instance.
     * @param spriteSheet The sprite sheet to copy.
     * @param nms The array of names of the Animations.
     * @param lengths The length of each Animation.
     */
    public CreatureAnimator(BufferedImage spriteSheet, String[] nms, final int[] lengths){
        List<ImageIcon> imgList = new LinkedList<>();
        for(int y=0, ych=spriteSheet.getHeight();y<ych;y+=16){
            for(int x=0, xch=spriteSheet.getWidth();x<xch;x+=16){
                imgList.add(new ImageIcon(spriteSheet.getSubimage(x, y, 16, 16)));
            }
        }
        int count = 0;
        for(int n=0;n<nms.length;n++){
            final LinkedList<ImageIcon> sublist = new LinkedList<>(imgList.subList(count, count+lengths[n]));
            count+=lengths[n];
            int a = n;
            map.put(nms[n], new LoadableAnimation((Serializable&Supplier<ImageIcon[]>)()->{
                    return sublist.toArray(new ImageIcon[lengths[a]]);
                }, 500, this));
        }
        active = map.get("stand");
        map.put("stand", new RandomAnimation((LoadableAnimation)active, 5, new Distribution(800, 801)));
        initializeReverse();
    }
    
    /**
     * Switches to a different animation.
     * @param name The name of the new animation.
     */
    @Override
    public void switchTo(String name){
        if(facingLeft) active = map.get(name);
        else active = reverse.get(name);
        currentName = name;
    }
    
    /**
     * Switches to a different animation and then back again after 1 cycle.
     * @param name The name of the new animation.
     */
    @Override
    public synchronized void switchAndBack(String name){
        nextName = currentName;
        switchTo(name);
    }
    
    /**
     * Switches to a new animation and then fades away.
     * @param name The new animation.
     */
    @Override
    public synchronized void switchFade(String name){
        switchTo(name);
        nextName = "fade";
    }

    @Override
    public synchronized void animationDone(Animation a){
        if(nextName!=null){
            if(nextName.equals("fade")){
                active = getFadeAnimation(active.frames[active.frames.length-1]);
                currentName = "fade";
            }else switchTo(nextName);
            nextName = null;
        }
    }
    
    /**
     * Gets a fade-away animation of a given image.
     * @param i
     * @return The Animation.
     */
    public FramedAnimation getFadeAnimation(ImageIcon i){
        return new LoadableAnimation((Serializable&Supplier<ImageIcon[]>)()->{
            BufferedImage bi = ImageUtils.convertToBuffered(i);
            ImageIcon[] ret = new ImageIcon[25];
            for(int n=0;n<25;n++)
                ret[n] = new ImageIcon(ImageUtils.fade(bi, 245-10*n));
            return ret;
        }, 5, this); 
    }
    
    /**
     * Animates the active Animation
     * @param g The Graphics
     * @param x The x coordinate
     * @param y The y coordinate
     */
    @Override
    public void animate(Graphics2D g, int x, int y){
        active.animate(g, x, y);
    }
    
    /**
     * Updates the orientation of this sprite after it moved.
     * @param x1 The first x coordinate.
     * @param x2 The next x coordinate.
     */
    public void updateOrientation(int x1, int x2){
        if(x2>x1){
            if(facingLeft){
                facingLeft = false;
                active = reverse.get(currentName);
            }
        }else if(x2<x1){
            if(!facingLeft){
                facingLeft = true;
                active = map.get(currentName);
            }
        }
    }
    
    private void initializeReverse(){
        reverse = new HashMap<>();
        map.entrySet().forEach((entry) -> {
            reverse.put(entry.getKey(), entry.getValue().mirror());
        });
    }
    
    private void readObject(ObjectInputStream in) 
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        initializeReverse();
    }
    
}
