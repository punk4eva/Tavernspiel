
package level;

import java.util.LinkedList;
import java.util.List;
import level.RoomDistribution.MakeRoom;
import logic.Distribution;

/**
 *
 * @author Adam Whittaker
 */
public class LevelFeeling{
    
    public final String description, backgroundMusicPath;
    public final Distribution roomDist, itemRoomDist, trapVisibleChance, 
            trapChance, waterGenChance, grassGenChance, grassUpgradeChance;
    public final List<MakeRoom> forcedRooms;
    public final boolean waterBeforeGrass;
    public final int difficulty;
    
    public LevelFeeling(String desc, String bgp, Distribution room, Distribution itRoom,
            Distribution trapVis, Distribution trap, Distribution wC,
            Distribution gC, Distribution guc, boolean wbg, int dif, List<MakeRoom>... fRooms){
        description = desc;
        roomDist = room;
        itemRoomDist = itRoom;
        trapVisibleChance = trapVis;
        difficulty = dif;
        trapChance = trap;
        if(fRooms.length==0) forcedRooms = new LinkedList<>(); 
        else forcedRooms = fRooms[0];
        waterGenChance = wC;
        waterBeforeGrass = wbg;
        grassUpgradeChance = guc;
        grassGenChance = gC;
        backgroundMusicPath = bgp;
    }
    
    public static final LevelFeeling STANDARD = new LevelFeeling(null,
            "Cyanoshrooms.wav",
            new Distribution(new int[]{10,6,6,2,1,3,3,7,5,2,1,1}), 
            new Distribution(new int[]{1,4,6,2}), new Distribution(1, 5),
            new Distribution(1, 35), new Distribution(1, 25),
            new Distribution(1, 25), new Distribution(1, 2), true, 1);
    
    
}
