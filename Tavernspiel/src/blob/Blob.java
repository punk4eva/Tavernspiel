
package blob;

import animation.GasAnimator;
import buffs.Buff;
import creatureLogic.Description;
import java.awt.Graphics2D;
import java.lang.reflect.Constructor;
import logic.GameObject;

/**
 *
 * @author Adam Whittaker
 *
 * This class models the behaviour of a spreading blob.
 */
public abstract class Blob extends GameObject{
    
    public final Buff buff;
    public int spreadNumber;
    
    /**
     * Creates a new instance.
     * @param n The name of the GameObject.
     * @param desc The Description.
     * @param b The Buff.
     * @param a The animation.
     * @param spread The spread number.
     * @param nx The x coordinate.
     * @param ny The y coordinate.
     */
    public Blob(String n, Description desc, Buff b, GasAnimator a, int spread, int nx, int ny){
        super(n, desc, a);
        buff = b;
        spreadNumber = spread;
        x = nx;
        y = ny;
    }
    
    /**
     * Spreads this Blob.
     */
    protected abstract void spread();

    @Override
    public void render(Graphics2D g, int fx, int fy){
        standardAnimation(g, fx, fy);
    }

    @Override
    public void turn(double delta){
        for(double d=delta+turndelta;d>=1;d--) spread();
        turndelta = (delta+turndelta)%1.0;
    }
    
}
