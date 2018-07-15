
package level;

import buffs.GasBuilder;
import containers.Chest;
import containers.Floor;
import items.Item;
import items.ItemMap;
import items.consumables.PotionBuilder;
import items.misc.Key;
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
    
    public static Room standard(Location loc, int depth){
        Room ret = Room.genStandard(loc, depth);
        ret.addDoors();
        ret.randomlyPlop();
        ret.addShaders();
        return ret;
    }
    
    public static Room standardLocked(Location loc, int depth){
        Room ret = Room.genStandard(loc, new Key(depth), ItemMap.getStandardItemMap(depth, loc));
        ret.addDoors();
        ret.randomlyPlop();
        ret.addShaders();
        return ret;
    }
    
    public static Room itemless(Location loc, int depth){
        Room ret = Room.genStandard(loc, depth);
        ret.addDoors();
        ret.addShaders();
        return ret;
    }
    
    public static Room lockedItemless(Location loc, int depth){
        Room ret = Room.genStandard(loc, new Key(depth), new ItemMap());
        ret.addDoors();
        ret.addShaders();
        return ret;
    }
    
    public static Room roomOfTraps(Location loc, Item item, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 10)), loc, depth);
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
        room.addShaders();
        return room;
    }
    
    public static Room chasmVault(Location loc, Item item, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 10)), loc, depth);
        room.paintAndPave();
        Tile pedestal = new Tile("pedestal", loc, true, false, true);
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
        room.addShaders();
        return room;
    }
    
    public static Room storage(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, PotionBuilder.flamePotion(), ItemMap.getStorageItemMap(depth, loc));
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) room.map[y][x] = Tile.wall(loc);
                else room.map[y][x] = new Tile("specialfloor", loc, true, false, true);
            }
        }
        room.barricade();
        room.randomlyPlop();
        room.addShaders();
        return room;
    }
    
    public static Room magicWellRoom(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, depth);
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
        room.addShaders();
        return room;
    }
    
    public static Room depthEntrance(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, depth);
        room.standardify();
        room.addDoors();
        room.map[room.dimension.height/2][room.dimension.width/2] =
                        new DepthEntrance(loc);
        room.startCoords = new Integer[]{room.dimension.width/2, room.dimension.height/2};
        room.randomlyPlop();
        room.addShaders();
        return room;
    }
    
    public static Room depthExit(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, depth);
        room.standardify();
        room.addDoors();
        room.map[room.dimension.height/2][room.dimension.width/2] =
                        new DepthExit(loc);
        room.endCoords = new Integer[]{room.dimension.width/2, room.dimension.height/2};
        room.randomlyPlop();
        room.addShaders();
        return room;
    }
    
    @Unfinished("Way too close to Pixel Dungeon")
    public static Room garden(Location location, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), location, depth);
        room.itemMap = ItemMap.getGardenItemMap(depth, location);
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) room.map[y][x] = Tile.wall(location);
                else{
                    if(y==1||x==1||y==room.dimension.height-2||x==room.dimension.width-2)
                        room.map[y][x] = new Grass(location, true);
                    else room.map[y][x] = new Grass(location, false);
                    room.addObject(GasBuilder.gardengas(x, y));
                }
            }
        }
        room.addDoors(1);
        room.randomlyPlop();
        room.addShaders();
        return room;
    } 
    
    public static Room floodedVault(Location loc, Item item){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 10),
                Distribution.getRandomInt(5, 10)), loc, -1);
        room.paintAndPave();
        Tile pedestal = new Tile("pedestal", loc, true, false, true);
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
        //@unfinished
        //room.spawnUncounted(CreatureBuilder.piranha(loc));
        room.addShaders();
        return room;
    }
    
    public static Room stalagnate(Location location, int depth){
        Room room = Room.genStandard(location, depth);
        room.addDoors();
        for(int y=1;y<room.dimension.height-1;y++)
            for(int x=1;x<room.dimension.width-1;x++)
                if(Distribution.chance(1, 7)) room.map[y][x] = Tile.wall(location);
        room.checkDoors();
        room.randomlyPlop();
        room.addShaders();
        return room;
    }
    
    public static Room maze(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(10, 32),
                Distribution.getRandomInt(10, 32)), loc, depth);
        new MazeBuilder(room, 0, 0, room.dimension.width, room.dimension.height);
        room.addShaders();
        room.randomlyPlop();
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
    
}
