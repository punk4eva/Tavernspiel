
package level;

import java.awt.Dimension;
import logic.Distribution;
import tiles.Tile;
import tiles.Trap;
import tiles.TrapBuilder;

/**
 *
 * @author Adam Whittaker
 * 
 * Builds rooms.
 */
public class RoomBuilder{
    
    
    private static final String[] TRAPCOLOURS = new String[]{
        "blue", "silver", "green", "bear", "red", "yellow", "orange", "purple"}; 
    
    
    public static Room standard(Location loc){
        return Room.genStandard(loc);
    }
    
    public static Room roomOfTraps(Location loc){
        Room room = new Room(new Dimension(Distribution.getRandomInclusiveInt(5, 16),
        Distribution.getRandomInclusiveInt(5, 16)), loc);
        room.standardify();
        switch(Distribution.getRandomInclusiveInt(1, 4)){
            case 1:
                break;
            case 2:
                break;      //UNFINISHED
            case 3:
                break;
            case 4:
                break;
        }
        return room;
    }
    
    public static Trap getRandomTrap(Location loc){
        String tr = TRAPCOLOURS[Distribution.getRandomInclusiveInt(0, TRAPCOLOURS.length)] + "trap";
        return TrapBuilder.getTrap(tr, loc);
    }
    
}
