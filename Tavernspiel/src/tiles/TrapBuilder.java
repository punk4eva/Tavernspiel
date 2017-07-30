
package tiles;

import animation.Animation;
import buffs.Buff;
import containers.Chest;
import containers.Floor;
import containers.Mimic;
import containers.Receptacle;
import containers.SkeletalRemains;
import exceptions.ReceptacleOverflowException;
import items.Item;
import level.Area;
import level.Location;
import logic.Distribution;
import logic.Gas;
import logic.ImageHandler;

/**
 *
 * @author Adam Whittaker
 */
public class TrapBuilder{
    
    public static Chasm getChasm(int x, int y, Location loc, Area area){
        return new Chasm(area.map[y-1][x].name, loc);
    }
    
    public static Trap getTrap(String tr, Area area){
        if(isGaseous(tr)){
            Gas g = getToxicGas(area); //UNFINISHED
            return new Trap(tr, area.location, g);
        }else{
            Buff b = new Buff("-1"); //UNFINISHED
            return new Trap(tr, area.location, b);
        }
    }
    
    public static boolean isGaseous(String str){
        return str.contains("yellow")||str.contains("green");
    }
    
    public static Gas getToxicGas(Area area){
        Buff b = new Buff("toxic gas", 1);
        b.damageDistribution = new Distribution(/**
                area.location.stageSpawnDistrib.incrementor-1, 
                area.location.stageSpawnDistrib.incrementor+1*/
        5, 6);              
        Animation a = new Animation(ImageHandler.getFrames("water", 0)); //@unfinished, placeholder
        return new Gas("Toxic Gas", "A poisonous green vapour.", b, a, 7, 
                area.zipcode);
    }
    
    public static Receptacle getRandomReceptacle(Item i, int x, int y){  
        try{ switch((int) new Distribution(new double[]{1, 2, 3, 4}, new int[]{10,4,1,2}).next()){
            case 1: return new Floor(i, x, y);
            case 2: return new Chest(i, x, y);
            case 3: return new Mimic(i, x, y);
            default: return new SkeletalRemains(i, x, y);
        }}catch(ReceptacleOverflowException ignore){}
        throw new UnsupportedOperationException("What?");
    }
    
}
