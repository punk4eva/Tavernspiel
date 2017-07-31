
package items.equipment;

import animation.Animation;
import items.ItemBuilder;
import javax.swing.ImageIcon;
import level.Location;
import listeners.AreaEvent;
import logic.Distribution;
import logic.Formula;

/**
 *
 * @author Adam Whittaker
 */
public class Wand extends RangedWeapon{
    
    public double rechargeSpeed = 1;
    public Formula rechargeFormula = new Formula();
    public final Animation firingAnimation;
    public int range = -1; //-1 if N/A.
    public Formula rangeFormula = null; //Null if no formula.
    public AreaEvent areaEvent; //Null if no AreaEvent.
    public final int blockingLevel;
    /**
     * 0: Travels through anything.
     * 1: Stops at destination.
     * 2: Stops at non-treadable tiles, but not destination.
     * 3: Stops at creatures and non-treadable tiles, but not destination.
     * 4: Stops at non-treadable tiles and destination.
     * 5: Stops at creatures and non-treadable tiles and destination.
     */
    
    /**
     * @param s The name of the Wand.
     * @param ic Its icon.
     * @param dur Its durability.
     * @param d Its action distribution.
     * @param sp Its speed. 
     */
    public Wand(String s, ImageIcon ic, int dur, Distribution d, double sp){
        super(s, ic, dur, d, -1, sp);
        firingAnimation = ItemBuilder.getWandAnimation(s);
        blockingLevel = ItemBuilder.getWandBlockingLevel(s);
        areaEvent = ItemBuilder.getWandAreaEvent(s);
    }

    public void setAndNotify(int x, int y, Location loc){
        areaEvent.setXY(x, y);
        loc.notify(areaEvent);
    }
    
    
    
}
