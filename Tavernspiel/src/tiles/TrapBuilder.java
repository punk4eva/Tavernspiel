
package tiles;

import tiles.assets.Chasm;
import animation.GasAnimator;
import animation.assets.WaterAnimation;
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
    
    public static Trap getTrap(String trapColor, Location loc){
        switch(trapColor){
            case "green": return greenTrap(loc);
            case "orange": return orangeTrap(loc);
            case "yellow": return yellowTrap(loc);
            case "purple": return purpleTrap(loc);
            case "red": return redTrap(loc);
            case "blue": return blueTrap(loc);
            case "bear": return bearTrap(loc);
            case "silver": return silverTrap(loc);
        }
        throw new IllegalStateException("Illegal trap color: " + trapColor);
    }
    
    public static boolean isGaseous(String str){
        return str.contains("yellow")||str.contains("green");
    }
    
    @Unfinished
    public static Blob getToxicGas(Location location, int x, int y){
        Buff b = BuffBuilder.toxicGas(location);           
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
    
    @Unfinished("Placeholder")
    private static Trap greenTrap(Location loc){
        return new BuffTrap("greentrap", loc, BuffBuilder.poison(loc.depth));
    }

    @Unfinished("Placeholder")
    private static Trap orangeTrap(Location loc){
        return new BuffTrap("orangetrap", loc, BuffBuilder.poison(loc.depth));
    }

    @Unfinished("Placeholder")
    private static Trap yellowTrap(Location loc){
        return new BuffTrap("yellowtrap", loc, BuffBuilder.poison(loc.depth));
    }

    private static Trap purpleTrap(Location loc){
        return new BuffTrap("purpletrap", loc, BuffBuilder.poison(loc.depth));
    }

    @Unfinished("Placeholder")
    private static Trap redTrap(Location loc){
        return new BuffTrap("redtrap", loc, BuffBuilder.poison(loc.depth));
    }

    @Unfinished("Placeholder")
    private static Trap blueTrap(Location loc){
        return new BuffTrap("bluetrap", loc, BuffBuilder.poison(loc.depth));
    }

    @Unfinished("Placeholder")
    private static Trap bearTrap(Location loc){
        return new BuffTrap("beartrap", loc, BuffBuilder.poison(loc.depth));
    }

    @Unfinished("Placeholder")
    private static Trap silverTrap(Location loc){
        return new BuffTrap("silvertrap", loc, BuffBuilder.poison(loc.depth));
    }
    
}
