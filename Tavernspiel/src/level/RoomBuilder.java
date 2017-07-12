
package level;

import buffs.GasBuilder;
import containers.Chest;
import containers.Floor;
import exceptions.ReceptacleOverflowException;
import items.Item;
import java.awt.Dimension;
import java.util.ArrayList;
import logic.Distribution;
import tiles.AnimatedTile;
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
                Distribution.getRandomInclusiveInt(5, 10)), loc);
        room.standardify();
        Trap trap = getRandomTrap(room);
        switch(Distribution.getRandomInclusiveInt(1, 4)){
            case 1: //North
                for(int y = 2; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Trap(trap);
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
                        room.map[y][x] = new Trap(trap);
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
                        room.map[y][x] = new Trap(trap);
                    }
                }
                if(Distribution.chance(1, 2)){
                    room.map[room.dimension.height-2][room.dimension.width/2].receptacle = 
                            new Chest(item);
                }else{
                    try{
                        room.map[room.dimension.height-2][room.dimension.width/2].receptacle
                                = new Floor(item);
                    }catch(ReceptacleOverflowException ignore){}
                }
                room.map[0][room.dimension.width/2] =
                        new Door(loc);
                break;
            case 4: //West
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 2; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Trap(trap);
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
                Distribution.getRandomInclusiveInt(5, 10)), loc);
        room.paintAndPave();
        Tile pedestal = new Tile("pedestal", loc);
        try{
            pedestal.receptacle = new Floor(item);
        }catch(ReceptacleOverflowException ignore){}
        switch(Distribution.getRandomInclusiveInt(1, 4)){
            case 1: //North
                for(int y = 1; y < room.dimension.height - 1; y++){
                    if(y==1) for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Chasm("wall", loc);
                    }
                    else for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Chasm("void", loc);
                    }
                }
                room.map[1][room.dimension.width/2] = pedestal;
                room.map[2][room.dimension.width/2] = new Chasm("floor", loc);
                room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc);
                break;
            case 2: //East
                for(int y = 1; y < room.dimension.height - 1; y++){
                    if(y==1) for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Chasm("wall", loc);
                    }
                    else for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Chasm("void", loc);
                    }
                }
                room.map[room.dimension.height/2][room.dimension.width-2] = pedestal;
                room.map[(room.dimension.height/2)+1][room.dimension.width-2] = new Chasm("floor", loc);
                room.map[room.dimension.height/2][0] = new Door(loc);
                break;
            case 3: //South
                for(int y = 1; y < room.dimension.height - 1; y++){
                    if(y==1) for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Chasm("wall", loc);
                    }
                    else for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Chasm("void", loc);
                    }
                }
                room.map[room.dimension.height-2][room.dimension.width/2] = pedestal;
                room.map[0][room.dimension.width/2] = new Door(loc);
                break;
            case 4: //West
                for(int y = 1; y < room.dimension.height - 1; y++){
                    if(y==1) for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Chasm("wall", loc);
                    }
                    else for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Chasm("void", loc);
                    }
                }
                room.map[room.dimension.height/2][1] = pedestal;
                room.map[(room.dimension.height/2)+1][1] = new Chasm("floor", loc);
                room.map[room.dimension.height/2][room.dimension.width-1] = new Door(loc);
                break;
        }
        return room;
    }
    
    public static Room storage(Location loc, ArrayList<Item> items){
        Room room = new Room(new Dimension(Distribution.getRandomInclusiveInt(5, 16),
                Distribution.getRandomInclusiveInt(5, 16)), loc);
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1){
                    if(Distribution.chance(1, 10)) room.map[y][x] = new Tile("specialwall", loc, false, false);
                    else room.map[y][x] = new Tile("wall", loc, false, false);
                }else room.map[y][x] = new Tile("specialfloor", loc);
            }
        }
        room.barricade();
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
    
    public static Room garden(Area area){
        Room room = new Room(new Dimension(Distribution.getRandomInclusiveInt(5, 16),
                Distribution.getRandomInclusiveInt(5, 16)), area.location);
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1){
                    if(Distribution.chance(1, 10)) room.map[y][x] = new Tile("specialwall", area.location, false, false);
                    else room.map[y][x] = new Tile("wall", area.location, false, false);
                }else{
                    if(y==1||x==1||y==room.dimension.height-2||x==room.dimension.width-2)
                        room.map[y][x] = new Tile("highgrass", area.location);
                    else room.map[y][x] = new Tile("lowgrass", area.location);
                    room.map[y][x].gas = GasBuilder.gardengas(area);
                }
            }
        }
        return room;
    } 
    
    public static Room floodedVault(Location loc, Item item){
        Room room = new Room(new Dimension(Distribution.getRandomInclusiveInt(5, 16),
                Distribution.getRandomInclusiveInt(5, 10)), loc);
        room.paintAndPave();
        Tile pedestal = new Tile("pedestal", loc);
        try{
            if(Distribution.chance(1, 3)) pedestal.receptacle = new Floor(item);
            else pedestal.receptacle = new Chest(item);
        }catch(ReceptacleOverflowException ignore){}
        switch(Distribution.getRandomInclusiveInt(1, 4)){
            case 1: //North
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new AnimatedTile("water", loc, x%2);
                    }
                }
                room.map[1][room.dimension.width/2] = pedestal;
                room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc);
                break;
            case 2: //East
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new AnimatedTile("water", loc, x%2);
                    }
                }
                room.map[room.dimension.height/2][room.dimension.width-2] = pedestal;
                room.map[room.dimension.height/2][0] = new Door(loc);
                break;
            case 3: //South
                for(int y = 1; y < room.dimension.height - 1; y++){
                   for(int x = 1; x < room.dimension.width - 1; x++)
                        room.map[y][x] = new AnimatedTile("water", loc, x%2);
                }
                room.map[room.dimension.height-2][room.dimension.width/2] = pedestal;
                room.map[0][room.dimension.width/2] = new Door(loc);
                break;
            case 4: //West
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new AnimatedTile("water", loc, x%2);
                    }
                }
                room.map[room.dimension.height/2][1] = pedestal;
                room.map[room.dimension.height/2][room.dimension.width-1] = new Door(loc);
                break;
        }
        //room.spawnUncounted(CreatureBuilder.piranha(loc));
        return room;
    }
                    
            
    
    public static Trap getRandomTrap(Area area){
        String tr = TRAPCOLOURS[Distribution.getRandomInclusiveInt(0, TRAPCOLOURS.length-1)] + "trap";
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
