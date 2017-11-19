
package logic;

import animation.GameObjectAnimator;
import creatureLogic.Description;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import level.Area;

/**
 *
 * @author Adam Whittaker
 * 
 * @TOPLEVEL
 */
public abstract class GameObject implements Serializable{
    
    private static final long serialVersionUID = -1805157903;

    public final String name;
    private double turnNum;
    private volatile boolean running = true;
    private CyclicBarrier barrier = new CyclicBarrier(2);
    protected Thread thread = new Thread(() -> {
        while(running){
            try{
                barrier.await();
            }catch(BrokenBarrierException | InterruptedException e){}
            barrier.reset();
            turn(turnNum);
        }
    });
    public final Description description;
    public final GameObjectAnimator animator;
    public volatile int x, y;
    public double turndelta = 0;
    public volatile Area area;
    
    /**
     * Creates a new instance.
     * @param n The name.
     * @param desc The description.
     * @param an The animator.
     */
    public GameObject(String n, Description desc, GameObjectAnimator an){
        name = n;
        description = desc;
        animator = an;
    }
    
    public void threadTurn(double delta){
        turnNum = delta;
        try{
            barrier.await();
        }catch(InterruptedException | BrokenBarrierException ex){}
    }
    
    public synchronized void start(){
        thread.start();
    }
    
    public synchronized void stop(){
        running = false;
        try{
            thread.join();
        }catch(InterruptedException e){}
    }
    
    /**
     * What the GameObject does each turn
     * @param delta The fraction of a turn consumed.
     */
    protected abstract void turn(double delta);
    
    /**
     * Rendering the GameObject
     * @param g The graphics to render the GameObject on.
     * @param focusX The x focus.
     * @param focusY The y focus.
     */
    public abstract void render(Graphics g, int focusX, int focusY);
    
    public void standardAnimation(Graphics g, int focusX, int focusY){
        animator.active.animate(g, x*16+focusX, y*16+focusY);
    }

    /**
     * Sets the GameObject's Area.
     * @param ar
     */
    public void setArea(Area ar){
        area = ar;
    }
    
}
