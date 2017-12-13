
package animation;

import gui.Window;
import items.Item;
import items.equipment.Wand;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;
import logic.Utils.Unfinished;
import pathfinding.Point;

/**
 *
 * @author Adam Whittaker
 */
public class StaticAnimator{
    
    public volatile static Animation current;
    private static Semaphore semaphore = new Semaphore(0);
    
    private StaticAnimator(){}
    
    public static void complete(){
        current.stop();
        current = null;
        semaphore.release();
    }
    
    private static void pause(){
        try{
            semaphore.acquire();
        }catch(InterruptedException ex){}
    }
    
    @Unfinished
    public static void throwItem(int x, int y, Item i, int x0, int y0){
        //queue.add(new Animation(Window.main));
        current = dummyAnimation();
        current.start();
        pause();
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
    public static void drawWandArc(Wand wand, int x, int y, int destx, int desty){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Displays a searching animation.
     * @param ary The list of points that was searched.
     * @param searchSuccessful Whether the search was successful.
     */
    public static void searchAnimation(List<Point> ary, boolean searchSuccessful){
        if(searchSuccessful) Window.main.soundSystem.playSFX("Misc/mystery.wav");
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Unfinished("Only used for test purposes so remove later.")
    private static Animation dummyAnimation(){
        return new Animation(new ImageIcon[]{new ImageIcon()}, Window.main);
    }
    
}
