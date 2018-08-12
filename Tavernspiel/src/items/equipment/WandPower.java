
package items.equipment;

import animation.Animation;
import logic.Formula;

/**
 *
 * @author Adam Whittaker
 */
public class WandPower{
    
    public double rechargeSpeed;
    public Formula rechargeFormula;
    public final Animation firingAnimation;
    public int range; //-1 if N/A.
    public Formula rangeFormula; //Null if no formula.
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
     * Creates a new instance.
     * @param reF
     * @param fA
     * @param bL
     * @param raF
     * @param level
     */
    public WandPower(Formula reF, Animation fA, Formula raF, int bL, int level){
        rechargeFormula = reF;
        firingAnimation = fA;
        rangeFormula = raF;
        blockingLevel = bL;
        range = (int)rangeFormula.get(level);
        rechargeSpeed = rechargeFormula.get(level);
    }
    
}
