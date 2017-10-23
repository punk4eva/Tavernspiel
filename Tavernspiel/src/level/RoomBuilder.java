
package level;

import buffs.GasBuilder;
import containers.Chest;
import containers.Floor;
import items.Item;
import items.ItemBuilder;
import items.Key;
import java.awt.Dimension;
import logic.Distribution;
import logic.Utils.Unfinished;
import pathfinding.MazeBuilder;
import tiles.*;

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
        Room ret = Room.genStandard(loc);
        ret.addDoors();
        ret.randomlyPlop();
        return ret;
    }
    
    public static Room itemless(Location loc){
        Room ret = Room.genStandard(loc);
        ret.addDoors();
        return ret;
    }
    
    public static Room lockedItemless(Location loc, int depth){
        Room ret = Room.genStandard(loc, new Key(depth), null);
        ret.addDoors();
        return ret;
    }
    
    public static Room roomOfTraps(Location loc, Item item){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 10)), loc);
        room.standardify();
        Trap trap = getRandomTrap(loc);
        switch(Distribution.getRandomInt(1, 4)){
            case 1: //North
                for(int y = 2; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new Trap(trap);
                    }
                }
                if(Distribution.chance(1, 2)){
                    room.receptacles.add(new Chest(item, room.dimension.width/2, 1));
                }else room.receptacles.add(new Floor(item, room.dimension.width / 2, 1));
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
                    room.receptacles.add(new Chest(item, room.dimension.width-2, room.dimension.height/2));
                }else room.receptacles.add(new Floor(item, room.dimension.width-2, room.dimension.height/2));
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
                    room.receptacles.add(new Chest(item, room.dimension.width/2, room.dimension.height-2));
                }else room.receptacles.add(new Floor(item, room.dimension.width/2, room.dimension.height-2));
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
                    room.receptacles.add(new Chest(item, 1, room.dimension.height/2));
                }else room.receptacles.add(new Floor(item, 1, room.dimension.height/2));
                room.map[room.dimension.height/2][room.dimension.width-1] =
                        new Door(loc);
                break;
        }
        return room;
    }
    
    public static Room chasmVault(Location loc, Item item){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 10)), loc);
        room.paintAndPave();
        Tile pedestal = new Tile("pedestal", loc);
        switch(Distribution.getRandomInt(1, 4)){
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
                room.receptacles.add(new Floor(item, room.dimension.width/2, 1));
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
                room.receptacles.add(new Floor(item, room.dimension.width-2, room.dimension.height/2));
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
                room.receptacles.add(new Floor(item, room.dimension.width/2, room.dimension.height-2));
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
                room.receptacles.add(new Floor(item, 1, room.dimension.height/2));
                room.map[(room.dimension.height/2)+1][1] = new Chasm("floor", loc);
                room.map[room.dimension.height/2][room.dimension.width-1] = new Door(loc);
                break;
        }
        return room;
    }
    
    public static Room storage(Location loc){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc);
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1){
                    if(Distribution.chance(1, 10)) room.map[y][x] = new Tile("specialwall", loc, false, false);
                    else room.map[y][x] = new Tile("wall", loc, false, false);
                }else room.map[y][x] = new Tile("specialfloor", loc);
            }
        }
        room.barricade();
        room.randomlyPlop();
        return room;
    }
    
    public static Room magicWellRoom(Location loc){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc);
        room.standardify();
        room.addDoors();
        switch(Distribution.getRandomInt(1, 3)){
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
    
    public static Room depthEntrance(Location loc){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc);
        room.standardify();
        room.addDoors();
        room.map[room.dimension.height/2][room.dimension.width/2] =
                        new DepthEntrance(loc);
        room.startCoords = new Integer[]{room.dimension.width/2, room.dimension.height/2};
        room.randomlyPlop();
        return room;
    }
    
    public static Room depthExit(Location loc){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc);
        room.standardify();
        room.addDoors();
        room.map[room.dimension.height/2][room.dimension.width/2] =
                        new DepthExit(loc);
        room.randomlyPlop();
        return room;
    }
    
    public static Room garden(Location location){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), location);
        room.itemMap = ItemBuilder.getGardenItemMap();
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1){
                    if(Distribution.chance(1, 10)) room.map[y][x] = new Tile("specialwall", location, false, false);
                    else room.map[y][x] = new Tile("wall", location, false, false);
                }else{
                    if(y==1||x==1||y==room.dimension.height-2||x==room.dimension.width-2)
                        room.map[y][x] = new Tile("highgrass", location);
                    else room.map[y][x] = new Tile("lowgrass", location);
                    room.addObject(GasBuilder.gardengas(x, y));
                }
            }
        }
        room.randomlyPlop();
        return room;
    } 
    
    public static Room floodedVault(Location loc, Item item){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 10),
                Distribution.getRandomInt(5, 10)), loc);
        room.paintAndPave();
        Tile pedestal = new Tile("pedestal", loc);
        switch(Distribution.getRandomInt(1, 4)){
            case 1: //North
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new AnimatedTile(loc, x%2);
                    }
                }
                room.map[1][room.dimension.width/2] = pedestal;
                if(Distribution.chance(1, 2)) room.receptacles.add(new Floor(item, room.dimension.width/2, 1));
                else room.receptacles.add(new Chest(item, room.dimension.width/2, 1));
                room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc);
                break;
            case 2: //East
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new AnimatedTile(loc, x%2);
                    }
                }
                room.map[room.dimension.height/2][room.dimension.width-2] = pedestal;
                if(Distribution.chance(1, 2)) room.receptacles.add(new Floor(item, room.dimension.width-2, room.dimension.height/2));
                else room.receptacles.add(new Chest(item, room.dimension.width-2, room.dimension.height/2));
                room.map[room.dimension.height/2][0] = new Door(loc);
                break;
            case 3: //South
                for(int y = 1; y < room.dimension.height - 1; y++){
                   for(int x = 1; x < room.dimension.width - 1; x++)
                        room.map[y][x] = new AnimatedTile(loc, x%2);
                }
                room.map[room.dimension.height-2][room.dimension.width/2] = pedestal;
                if(Distribution.chance(1, 2)) room.receptacles.add(new Floor(item, room.dimension.width/2, room.dimension.height-2));
                else room.receptacles.add(new Chest(item, room.dimension.width/2, room.dimension.height-2));
                room.map[0][room.dimension.width/2] = new Door(loc);
                break;
            case 4: //West
                for(int y = 1; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = new AnimatedTile(loc, x%2);
                    }
                }
                room.map[room.dimension.height/2][1] = pedestal;
                if(Distribution.chance(1, 2)) room.receptacles.add(new Floor(item, 1, room.dimension.height/2));
                else room.receptacles.add(new Chest(item, 1, room.dimension.height/2));
                room.map[room.dimension.height/2][room.dimension.width-1] = new Door(loc);
                break;
        }
        //room.spawnUncounted(CreatureBuilder.piranha(loc));
        return room;
    }
    
    public static Room stalagnate(Location location){
        Room room = Room.genStandard(location);
        room.addDoors();
        for(int y=1;y<room.dimension.height-1;y++){
            for(int x=1;x<room.dimension.width-1;x++){
                if(Distribution.chance(1, 7)){
                    if(Distribution.chance(1, 10)) room.map[y][x] = new Tile("specialwall", location);
                    else room.map[y][x] = new Tile("wall", location);
                }
            }
        }
        room.randomlyPlop();
        return room;
    }
    
    public static Room maze(Location loc, int width, int height){
        Room room = new Room(new Dimension(width, height), loc);
        new MazeBuilder(room, 0, 0, width, height);
        return room;
    }
                    
            
    
    public static Trap getRandomTrap(Location loc){
        String tr = TRAPCOLOURS[Distribution.getRandomInt(0, TRAPCOLOURS.length-1)] + "trap";
        return TrapBuilder.getTrap(tr, loc);
    }
    
    public static Tile getRandomTrapOrChasm(Area area, int x, int y){
        if(Distribution.chance(9, 10)){
            String tr = TRAPCOLOURS[Distribution.getRandomInt(0, TRAPCOLOURS.length)] + "trap";
            return TrapBuilder.getTrap(tr, area.location);
        }else{
            return new Chasm(area, x, y);
        }
    }

    @Unfinished
    public static RoomDistribution getNormalRoomDistribution(){
        return null;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
