
package tiles;

import animation.GasAnimator;
import animation.WaterAnimation;
import blob.Blob;
import buffs.Buff;
import buffs.BuffBuilder;
import containers.Chest;
import containers.Floor;
import containers.Mimic;
import containers.Receptacle;
import containers.SkeletalRemains;
import creatureLogic.Description;
import items.Item;
import level.Area;
import level.Location;
import logic.Distribution;
import logic.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class TrapBuilder{
    
    private static final Distribution receptacleDistrib = new Distribution(new double[]{1, 2, 3, 4}, new int[]{10,4,1,2});
    
    public static Chasm getChasm(int x, int y, Location loc, Area area){
        return new Chasm(area.map[y-1][x].name, loc);
    }
    
    @Unfinished
    public static Trap getTrap(String tr, Location location){
        if(isGaseous(tr)){
            Blob g = getToxicGas(location, Integer.MIN_VALUE, Integer.MIN_VALUE); //@unfinished
            return new Trap(tr, location, g);
        }else{
            Buff b = BuffBuilder.getTrapBuff(tr); //@unfinished
            return new Trap(tr, location, b);
        }
    }
    
    public static boolean isGaseous(String str){
        return str.contains("yellow")||str.contains("green");
    }
    
    @Unfinished
    public static Blob getToxicGas(Location location, int x, int y){
        Buff b = BuffBuilder.toxicGas();           
        GasAnimator a = new GasAnimator(new WaterAnimation(location, 0)); //@unfinished, placeholder
        return new Blob("Toxic Gas", new Description("gas", "A poisonous green vapour."), b, a, 7, x, y);
    }
    
    public static Receptacle getRandomReceptacle(Item i, int x, int y){  
        switch((int) receptacleDistrib.next()){
            case 1: return new Floor(i, x, y);
            case 2: return new Chest(i, x, y);
            case 3: return new Mimic(i, x, y);
            default: return new SkeletalRemains(i, x, y);
        }
    }
    
}
