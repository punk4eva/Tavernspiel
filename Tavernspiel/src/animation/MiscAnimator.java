
package animation;

import gui.Window;
import items.Item;
import items.equipment.Wand;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import listeners.AnimationListener;
import logic.Utils.Unfinished;
import pathfinding.Point;

/**
 *
 * @author Adam Whittaker
 * 
 * This class handles static animations of non-object things such as wand shots.
 */
public class MiscAnimator implements AnimationListener{
    
    public final List<Animation> current = new LinkedList<>();
    
    /**
     * Starts animating an Animation.
     * @param a
     */
    public void addAnimation(Animation a){
        synchronized(current){
            current.add(a);
        }
    }
    
    /**
     * Animates all current animations registered with this Animator.
     * Note that the x and y coordinates are of the focus, not the top-left,
     * since the animations aren't object-bound, so they will have to position
     * themselves, meaning the animate method should be overriden.
     * @param g The graphics
     * @param fx The focusX
     * @param fy The focusY
     */
    public void animate(Graphics g, int fx, int fy){
        current.removeIf(a -> a.done);
        current.stream().forEach(a -> a.animate(g, fx, fy));
    }
    
    /**
     * Animates an Item being thrown.
     * @param x The x of the starting tile.
     * @param y The y of the starting tile.
     * @param i The item.
     * @param x0 The x of the ending tile.
     * @param y0 The y of the ending tile.
     */
    @Unfinished
    public void throwItem(int x, int y, Item i, int x0, int y0){
        //queue.add(new Animation(Window.main));
    }
    
    /**
     * Draws a wand arc from the given wand using the given coordinates.
     * @param wand The wand to draw.
     * @param x The starting x coordinate.
     * @param y The starting y coordinate.
     * @param destx The destination x coordinate.
     * @param desty The destination y coordinate.
     */
    @Unfinished
    public void drawWandArc(Wand wand, int x, int y, int destx, int desty){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Displays a searching animation.
     * @param ary The list of points that was searched.
     * @param searchSuccessful Whether the search was successful.
     */
    public void searchAnimation(List<Point> ary, boolean searchSuccessful){
        if(searchSuccessful) Window.main.soundSystem.playSFX("Misc/mystery.wav");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Registers that an animation is done. All animations need to have the done
     * variable set to true if they want to end.
     * @param a The ending Animation.
     */
    @Override
    public void done(Animation a){}
    
}
