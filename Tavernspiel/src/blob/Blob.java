
package blob;

import animation.GameObjectAnimator;
import buffs.Buff;
import creatureLogic.Description;
import java.awt.Graphics;
import java.util.LinkedList;
import logic.GameObject;

/**
 *
 * @author Adam Whittaker
 *
 * This class represents a GameObject with an expanding cloud behavior.
 */
public class Blob extends GameObject{
    
    public LinkedList<Buff> buffs = new LinkedList<>();
    public int spreadNumber;
    
    /**
     * Creates a blob.
     * @param n The name.
     * @param desc The description.
     * @param b The buff that it gives.
     * @param a The animator.
     * @param spread The spread number.
     * @param nx The x.
     * @param ny The y.
     */
    public Blob(String n, Description desc, Buff b, GameObjectAnimator a, int spread, int nx, int ny){
        super(n, desc, a);
        buffs.add(b);
        spreadNumber = spread;
        x = nx;
        y = ny;
    }
    
    /**
     * Creates a blob from a preexisting blob.
     * @param gas The parent blob.
     * @param nx The new x coordinate.
     * @param ny The new y coordinate.
     */
    public Blob(Blob gas, int nx, int ny){
        super(gas.name, gas.description, gas.animator);
        buffs = gas.buffs;
        x = nx;
        y = ny;
        spreadNumber = gas.spreadNumber-1;
    }
    
    /**
     * Spreads the blob to adjacent tiles and decrements spread number.
     */
    protected void spread(){
        if(spreadNumber==0){
            area.removeObject(this);
            return;
        }
        if(area.map[y-1][x].treadable) area.addObject(new Blob(this, x, y-1));
        if(area.map[y+1][x].treadable) area.addObject(new Blob(this, x, y+1));
        if(area.map[y][x-1].treadable) area.addObject(new Blob(this, x-1, y));
        if(area.map[y][x+1].treadable) area.addObject(new Blob(this, x+1, y));
        spreadNumber--;
    }

    @Override
    public void render(Graphics g, int fx, int fy){
        standardAnimation(g, fx, fy);
    }

    @Override
    public void turn(double delta){
        for(double d=delta+turndelta;d>=1;d--) spread();
        turndelta = (delta+turndelta)%1.0;
    }
    
}
