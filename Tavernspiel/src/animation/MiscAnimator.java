
package animation;

import animation.assets.ThrowAnimation;
import gui.mainToolbox.Main;
import items.Item;
import items.equipment.Wand;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import listeners.AnimationListener;
import logic.Utils.Unfinished;
import pathfinding.Point;

/**
 *
 * @author Adam Whittaker
 */
public class MiscAnimator implements AnimationListener{
    
    public final List<TrackableAnimation> current = new LinkedList<>();
    
    /**
     * Adds an animation to the list of animations currently displayed.
     * @param a
     */
    public void addAnimation(TrackableAnimation a){
        synchronized(current){
            current.add(a);
        }
    }
    
    /**
     * Paints the current list of animations on the Canvas.
     * @param g
     * @param fx
     * @param fy
     */
    public void animate(Graphics2D g, int fx, int fy){
        synchronized(current){
            current.removeIf(a -> a.done);
            current.stream().forEach(a -> a.animate(g, fx, fy));
        }
    }
    
    /**
     * Animates an Item throw.
     * @param i The Item
     * @param fx The starting x
     * @param fy The starting y
     * @param tx The dest x
     * @param ty The dest y
     */
    public void drawItemThrow(Item i, int fx, int fy, int tx, int ty){
        ThrowAnimation an = new ThrowAnimation(i, fx, fy, tx, ty, this); 
        addAnimation(an);
        captureTurnThread(an);
    }
    
    /**
     * Tells the TurnThread to wait until the given Animation is over.
     * @param a
     */
    private void captureTurnThread(TrackableAnimation a){
        synchronized(a){
            while(!a.done){
                try{
                    a.wait();
                }catch(InterruptedException e){}
            }
        }
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
        if(searchSuccessful) Main.soundSystem.playSFX("Misc/mystery.wav");
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Unfinished("Only used for test purposes so remove later.")
    private Animation dummyAnimation(){
        //return new Animation(new ImageIcon[]{new ImageIcon()}, this);
        throw new UnsupportedOperationException("No");
    }

    @Override
    public void animationDone(Animation a){
        synchronized(a){
            a.notify();
        }
    }
    
}
