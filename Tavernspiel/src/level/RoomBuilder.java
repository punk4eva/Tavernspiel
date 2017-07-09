
package level;

import containers.Chest;
import containers.Floor;
import exceptions.ReceptacleOverflowException;
import items.Item;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Distribution;
import tiles.Chasm;
import tiles.Door;
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
    
    public static Room roomOfTraps(Location loc, Item item){
        Room room = new Room(new Dimension(Distribution.getRandomInclusiveInt(5, 16),
                Distribution.getRandomInclusiveInt(5, 16)), loc);
        room.standardify();
        Trap trap = RoomBuilder.getRandomTrap(room);
        switch(Distribution.getRandomInclusiveInt(1, 4)){
            case 1: //North
                for(int y = 2; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        if(room.map[y][x].equals("floor") && Distribution.chance(1, 30)){
                            room.map[y][x] = trap;
                        }
                    }
                }
                if(Distribution.chance(1, 2)){
                    room.map[1][room.dimension.width/2].receptacle = 
                            new Chest(item);
                }else{
                    try{
                        room.map[1][room.dimension.width / 2].receptacle
                                = new Floor(item);
                    }catch(ReceptacleOverflowException ignore){}
                }
                room.map[room.dimension.height-1][room.dimension.width/2] =
                        new Door(loc);
                break;
            case 2: //East
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 2; x++){
                        if(room.map[y][x].equals("floor") && Distribution.chance(1, 30)){
                            room.map[y][x] = trap;
                        }
                    }
                }
                if(Distribution.chance(1, 2)){
                    room.map[room.dimension.height/2][room.dimension.width-2].receptacle = 
                            new Chest(item);
                }else{
                    try{
                        room.map[room.dimension.height/2][room.dimension.width-2].receptacle
                                = new Floor(item);
                    }catch(ReceptacleOverflowException ignore){}
                }
                room.map[room.dimension.height/2][0] =
                        new Door(loc);
                break;
            case 3: //South
                for(int y = 1; y < room.dimension.height - 2; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        if(room.map[y][x].equals("floor") && Distribution.chance(1, 30)){
                            room.map[y][x] = trap;
                        }
                    }
                }
                if(Distribution.chance(1, 2)){
                    room.map[1][room.dimension.width/2].receptacle = 
                            new Chest(item);
                }else{
                    try{
                        room.map[1][room.dimension.width/2].receptacle
                                = new Floor(item);
                    }catch(ReceptacleOverflowException ignore){}
                }
                room.map[room.dimension.height-1][room.dimension.width/2] =
                        new Door(loc);
                break;
            case 4: //West
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 2; x < room.dimension.width - 1; x++){
                        if(room.map[y][x].equals("floor") && Distribution.chance(1, 30)){
                            room.map[y][x] = trap;
                        }
                    }
                }
                if(Distribution.chance(1, 2)){
                    room.map[room.dimension.height/2][1].receptacle = 
                            new Chest(item);
                }else{
                    try{
                        room.map[room.dimension.height/2][1].receptacle
                                = new Floor(item);
                    }catch(ReceptacleOverflowException ignore){}
                }
                room.map[room.dimension.height/2][room.dimension.width-1] =
                        new Door(loc);
                break;
        }
        return room;
    }
    
    public static Trap getRandomTrap(Area area){
        String tr = TRAPCOLOURS[Distribution.getRandomInclusiveInt(0, TRAPCOLOURS.length)] + "trap";
        return TrapBuilder.getTrap(tr, area);
    }
    
    public static Tile getRandomTrapOrChasm(Area area, int x, int y){
        if(Distribution.chance(9, 10)){
            String tr = TRAPCOLOURS[Distribution.getRandomInclusiveInt(0, TRAPCOLOURS.length)] + "trap";
            return TrapBuilder.getTrap(tr, area);
        }else{
            return new Chasm(area, x, y, area.location);
        }
    }
    
}
