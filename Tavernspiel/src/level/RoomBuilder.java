
package level;

import containers.Chest;
import containers.Floor;
import exceptions.ReceptacleOverflowException;
import items.Item;
import java.awt.Dimension;
import java.util.ArrayList;
import logic.Distribution;
import tiles.Chasm;
import tiles.Door;
import tiles.Tile;
import tiles.Trap;
import tiles.TrapBuilder;
import tiles.Well;

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
        Room room = Room.genStandard(loc);
        room.addDoors();
        return room;
    }
    
    public static Room roomOfTraps(Location loc, Item item){
        Room room = new Room(new Dimension(Distribution.getRandomInclusiveInt(5, 16),
                Distribution.getRandomInclusiveInt(5, 16)), loc);
        room.standardify();
        Trap trap = getRandomTrap(room);
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
    
    public static Room chasmVault(Location loc, Item item){
        Room room = new Room(new Dimension(Distribution.getRandomInclusiveInt(5, 16),
                Distribution.getRandomInclusiveInt(5, 16)), loc);
        room.paintAndPave();
        Chasm chasm = new Chasm("void", loc);
        Chasm floorchasm = new Chasm("floor", loc);
        Chasm wallchasm = new Chasm("wall", loc);
        Tile pedestal = new Tile("pedestal", loc);
        try{
            pedestal.receptacle = new Floor(item);
        }catch(ReceptacleOverflowException ignore){}
        switch(Distribution.getRandomInclusiveInt(1, 4)){
            case 1: //North
                for(int y = 1; y < room.dimension.height - 1; y++){
                    if(y==1) for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = wallchasm;
                    }
                    else for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = chasm;
                    }
                }
                room.map[1][room.dimension.width/2] = pedestal;
                room.map[2][room.dimension.width/2] = floorchasm;
                room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc);
                break;
            case 2: //East
                for(int y = 1; y < room.dimension.height - 1; y++){
                    if(y==1) for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = wallchasm;
                    }
                    else for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = chasm;
                    }
                }
                room.map[room.dimension.height/2][room.dimension.width-2] = pedestal;
                room.map[(room.dimension.height/2)+1][room.dimension.width-2] = floorchasm;
                room.map[room.dimension.height/2][0] = new Door(loc);
                break;
            case 3: //South
                for(int y = 1; y < room.dimension.height - 1; y++){
                    if(y==1) for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = wallchasm;
                    }
                    else for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = chasm;
                    }
                }
                room.map[room.dimension.height-2][room.dimension.width/2] = pedestal;
                room.map[1][room.dimension.width/2] = floorchasm;
                room.map[0][room.dimension.width/2] = new Door(loc);
                break;
            case 4: //West
                for(int y = 1; y < room.dimension.height - 1; y++){
                    if(y==1) for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = wallchasm;
                    }
                    else for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = chasm;
                    }
                }
                room.map[room.dimension.height/2][1] = pedestal;
                room.map[(room.dimension.height/2)+1][1] = floorchasm;
                room.map[room.dimension.height/2][room.dimension.width-1] = new Door(loc);
                break;
        }
        return room;
    }
    
    public static Room storage(Location loc, ArrayList<Item> items){
        Room room = new Room(new Dimension(Distribution.getRandomInclusiveInt(5, 16),
                Distribution.getRandomInclusiveInt(5, 16)), loc);
        Tile specfloor = new Tile("specialfloor", loc);
        Tile specwall = new Tile("specialwall", loc);
        Tile wall = new Tile("wall", loc);
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1){
                    if(Distribution.chance(1, 10)) room.map[y][x] = specwall;
                    else room.map[y][x] = wall;
                }else room.map[y][x] = specfloor;
            }
        }
        room.randomlyPlop(items);
        return room;
    }
    
    public static Room magicWellRoom(Location loc){
        Room room = standard(loc);
        switch(Distribution.getRandomInclusiveInt(1, 3)){
            case 1:
                room.map[room.dimension.height/2][room.dimension.width/2] =
                        new Well("transmutation", loc);
                break;
            case 2:
                room.map[room.dimension.height/2][room.dimension.width/2] =
                        new Well("health", loc);
                break;
            case 3:
                room.map[room.dimension.height/2][room.dimension.width/2] =
                        new Well("knowledge", loc);
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
