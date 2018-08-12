
package level;

import java.util.LinkedList;
import java.util.List;
import level.RoomDistribution.MakeRoom;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public final class LevelFeeling{
    
    public final String description, backgroundMusicPath;
    public final Distribution roomDist, itemRoomDist, trapVisibleChance, 
            trapChance, waterGenChance, grassGenChance, grassUpgradeChance,
            wallChance, floorChance, doorHideChance, receptacleDist,
            wandTypeChance, wandWoodChance;
    public final List<MakeRoom> forcedRooms;
    public final boolean waterBeforeGrass;
    public final int difficulty, visibility;
    
    private LevelFeeling(String desc, String bgp, Distribution room, Distribution itRoom,
            Distribution trapVis, Distribution trap, Distribution wC,
            Distribution gC, Distribution guc, Distribution wallCh,
            Distribution floorCh, Distribution dhc, Distribution recDist,
            Distribution wandTC, Distribution wandWC,
            boolean wbg, int dif, int vis, List<MakeRoom>... fRooms){
        description = desc;
        wallChance = wallCh;
        floorChance = floorCh;
        roomDist = room;
        itemRoomDist = itRoom;
        trapVisibleChance = trapVis;
        doorHideChance = dhc;
        difficulty = dif;
        trapChance = trap;
        if(fRooms.length==0) forcedRooms = new LinkedList<>(); 
        else forcedRooms = fRooms[0];
        waterGenChance = wC;
        waterBeforeGrass = wbg;
        grassUpgradeChance = guc;
        grassGenChance = gC;
        backgroundMusicPath = bgp;
        wandTypeChance = wandTC;
        wandWoodChance = wandWC;
        visibility = vis;
        receptacleDist = recDist;
    }
    
    public static final Distribution feelingDist = new Distribution(new int[]{11,3,3,1,2});
    
    public static final LevelFeeling getRandomFeeling(){
        switch((int)feelingDist.next()){
            case 0: return STANDARD;
            case 1: return GRASS;
            case 2: return WATER;
            case 3: return BURIED_CITY;
            case 4: return BURNED;
        }
        throw new IllegalStateException();
    }
    
    
    
    public static final LevelFeeling STANDARD = new LevelFeeling(null,
            "Cyanoshrooms.wav",
            new Distribution(new int[]{20,11,13,3,1,5,6,13,10,4,2,3,3,1}), 
            new Distribution(new int[]{1,4,6,2}), new Distribution(1, 5),
            new Distribution(1, 35), new Distribution(1, 25),
            new Distribution(1, 25), new Distribution(1, 2), 
            new Distribution(1, 22), new Distribution(1, 22), 
            new Distribution(1, 10), new Distribution(new int[]{10,4,1,2}), 
            new Distribution(new int[]{}), new Distribution(new int[]{}), 
            true, 1, 6);
    public static final LevelFeeling GRASS = new LevelFeeling(
            "Thick vegetation hides your surroundings...", "Cyanoshrooms.wav",
            new Distribution(new int[]{16,8,12,6,1,6,7,11,12,8,3,4,4,2}), 
            new Distribution(new int[]{1,5,7,1}), new Distribution(2, 9),
            new Distribution(1, 30), new Distribution(1, 28),
            new Distribution(1, 16), new Distribution(6, 11), 
            new Distribution(1, 18), new Distribution(1, 18), 
            new Distribution(1, 9), new Distribution(new int[]{8,3,1,3}), 
            new Distribution(new int[]{}), new Distribution(new int[]{}), 
            false, 1, 5);
    public static final LevelFeeling WATER = new LevelFeeling(
            "Your feet feel wet...", "Cyanoshrooms.wav",
            new Distribution(new int[]{22,12,12,1,1,4,5,14,8,2,3,2,2,1}), 
            new Distribution(new int[]{2,3,5,3}), new Distribution(1, 6),
            new Distribution(1, 38), new Distribution(1, 16),
            new Distribution(1, 28), new Distribution(1, 3), 
            new Distribution(1, 30), new Distribution(0, 1), 
            new Distribution(1, 11), new Distribution(new int[]{11,3,1,1}), 
            new Distribution(new int[]{}), new Distribution(new int[]{}), 
            true, 1, 6);
    public static final LevelFeeling BURIED_CITY = new LevelFeeling(
            "This looks like the ruins of an ancient civilization...",
            "Cyanoshrooms.wav",
            new Distribution(new int[]{13,6,4,6,2,8,11,20,12,5,6,9,9,3}), 
            new Distribution(new int[]{1,1,1,6}), new Distribution(1, 20),
            new Distribution(1, 27), new Distribution(1, 27),
            new Distribution(1, 25), new Distribution(10, 19), 
            new Distribution(0, 1), new Distribution(1, 16), 
            new Distribution(1, 8), new Distribution(new int[]{6,3,1,4}), 
            new Distribution(new int[]{}), new Distribution(new int[]{}), 
            false, 1, 6);
    public static final LevelFeeling BURNED = new LevelFeeling(
            "The smell of ash wafts into your nose...", "Cyanoshrooms.wav",
            new Distribution(new int[]{21,12,15,1,1,5,3,13,13,12,1,1,1,1}), 
            new Distribution(new int[]{1,4,4,2}), new Distribution(2, 9),
            new Distribution(1, 40), new Distribution(1, 32),
            new Distribution(1, 29), new Distribution(4, 7), 
            new Distribution(1, 22), new Distribution(1, 21), 
            new Distribution(1, 15), new Distribution(new int[]{10,6,1,4}), 
            new Distribution(new int[]{}), new Distribution(new int[]{}), 
            false, 1, 6);
    
    
}
