
package tiles;

import animation.Animation;
import animation.GameObjectAnimator;
import buffs.Buff;
import containers.Chest;
import containers.Floor;
import containers.Mimic;
import containers.Receptacle;
import containers.SkeletalRemains;
import creatureLogic.Description;
import gui.Handler;
import items.Item;
import level.Area;
import level.Location;
import logic.Distribution;
import logic.Gas;
import logic.ImageHandler;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class TrapBuilder{
    
    public static Chasm getChasm(int x, int y, Location loc, Area area){
        return new Chasm(area.map[y-1][x].name, loc);
    }
    
    @Unfinished
    public static Trap getTrap(String tr, Location location, Handler handler){
        if(isGaseous(tr)){
            Gas g = getToxicGas(location, handler); //@unfinished
            return new Trap(tr, location, g);
        }else{
            Buff b = new Buff("-1"); //@unfinished
            return new Trap(tr, location, b);
        }
    }
    
    public static boolean isGaseous(String str){
        return str.contains("yellow")||str.contains("green");
    }
    
    @Unfinished
    public static Gas getToxicGas(Location location, Handler handler){
        Buff b = new Buff("toxic gas", 1);
        b.damageDistribution = new Distribution(/**
                area.location.stageSpawnDistrib.incrementor-1, 
                area.location.stageSpawnDistrib.incrementor+1*/
        5, 6);              
        GameObjectAnimator a = new GameObjectAnimator(new String[]{"placeholder"},
                new Animation[]{new Animation(ImageHandler.getWaterFrames(location, 0))}); //@unfinished, placeholder
        return new Gas("Toxic Gas", new Description("gas", "A poisonous green vapour."), b, a, 7, handler);
    }
    
    public static Receptacle getRandomReceptacle(Item i, int x, int y){  
        switch((int) new Distribution(new double[]{1, 2, 3, 4}, new int[]{10,4,1,2}).next()){
            case 1: return new Floor(i, x, y);
            case 2: return new Chest(i, x, y);
            case 3: return new Mimic(i, x, y);
            default: return new SkeletalRemains(i, x, y);
        }
    }
    
}
