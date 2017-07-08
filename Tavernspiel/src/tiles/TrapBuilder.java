
package tiles;

import animation.Animation;
import buffs.Buff;
import level.Location;
import logic.Distribution;
import logic.Gas;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class TrapBuilder{
    
    public static Trap getTrap(String tr, Location loc){
        if(isGaseous(tr)){
            Gas g = getToxicGas(loc); //UNFINISHED
            return new Trap(tr, loc, g);
        }else{
            Buff b = new Buff(); //UNFINISHED
            return new Trap(tr, loc, b);
        }
    }
    
    public static boolean isGaseous(String str){
        return str.contains("yellow")||str.contains("green");
    }
    
    public static Gas getToxicGas(Location loc){
        Buff b = new Buff();
        b.damageDistribution = new Distribution(
                loc.stageSpawnDistrib.incrementor-1, 
                loc.stageSpawnDistrib.incrementor+1);
        Animation a = new Animation(ImageHandler.getFrames("", 0)); //UNFINISHED
        return new Gas("Toxic Gas", "A poisonous green vapour.", b, a, 7);
    }
    
}
