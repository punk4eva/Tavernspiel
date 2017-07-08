
package level;

import java.awt.Dimension;
import logic.Distribution;
import tiles.Chasm;
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
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        if(room.map[y][x].equals("floor") && Distribution.chance(1, 30)){
                            room.map[y][x] = RoomBuilder.getRandomTrap(room.location);
                        }
                    }
                }
                break;
            case 2:
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        if(room.map[y][x].equals("floor") && Distribution.chance(1, 30)){
                            room.map[y][x] = RoomBuilder.getRandomTrap(room.location);
                        }
                    }
                }
                break;
            case 3:
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        if(room.map[y][x].equals("floor") && Distribution.chance(1, 30)){
                            room.map[y][x] = RoomBuilder.getRandomTrap(room.location);
                        }
                    }
                }
                break;
            case 4:
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        if(room.map[y][x].equals("floor") && Distribution.chance(1, 30)){
                            room.map[y][x] = RoomBuilder.getRandomTrap(room.location);
                        }
                    }
                }
                break;
        }
        return room;
    }
    
    public static Trap getRandomTrap(Location loc){
        String tr = TRAPCOLOURS[Distribution.getRandomInclusiveInt(0, TRAPCOLOURS.length)] + "trap";
        return TrapBuilder.getTrap(tr, loc);
    }
    
    public static Tile getRandomTrapOrChasm(Location loc, Area area, int x, int y){
        if(Distribution.chance(9, 10)){
            String tr = TRAPCOLOURS[Distribution.getRandomInclusiveInt(0, TRAPCOLOURS.length)] + "trap";
            return TrapBuilder.getTrap(tr, loc);
        }else{
            return new Chasm(area, x, y, loc);
        }
    }
    
}
