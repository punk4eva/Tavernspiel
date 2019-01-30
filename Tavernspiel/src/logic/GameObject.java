
package logic;

import animation.GameObjectAnimator;
import creatureLogic.Description;
import java.awt.Graphics2D;
import java.io.Serializable;
import level.Area;

/**
 *
 * @author Adam Whittaker
 * 
 * The base class for all Entities that can exist in a game.
 * @TOPLEVEL
 */
public abstract class GameObject implements Serializable{
    
    private static final long serialVersionUID = -1805157903;

    public final String name;
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
    
    /**
     * What the GameObject does each turn
     * @param delta The fraction of a turn consumed.
     */
    public abstract void turn(double delta);
    
    /**
     * Rendering the GameObject
     * @param g The graphics to render the GameObject on.
     * @param focusX The x focus.
     * @param focusY The y focus.
     */
    public abstract void render(Graphics2D g, int focusX, int focusY);
    
    /**
     * Animates the currently active animation.
     * @param g The Graphics
     * @param focusX
     * @param focusY
     */
    public void standardAnimation(Graphics2D g, int focusX, int focusY){
        animator.animate(g, x*16+focusX, y*16+focusY);
    }

    /**
     * Sets the GameObject's Area.
     * @param ar
     * @param start Whether to put them on the start or end coordinates.
     */
    public void setArea(Area ar, boolean start){
        area = ar;
    }
    
}
