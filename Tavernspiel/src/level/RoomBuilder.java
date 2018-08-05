
package level;

import tiles.assets.*;
import buffs.GasBuilder;
import containers.Chest;
import containers.Floor;
import items.Item;
import items.ItemMap;
import items.consumables.PotionBuilder;
import items.misc.Key;
import java.awt.Dimension;
import java.util.function.Function;
import logic.Distribution;
import logic.Utils.Unfinished;
import pathfinding.MazeBuilder;
import tiles.AnimatedTile;
import tiles.CustomTile;
import tiles.Tile;
import tiles.Trap;
import tiles.TrapBuilder;

/**
 *
 * @author Adam Whittaker
 * 
 * Builds rooms.
 */
public abstract class RoomBuilder{
    
    
    private static final String[] TRAPCOLOURS = new String[]{
        "blue", "silver", "green", "bear", "red", "yellow", "orange", "purple"}; 
    
    public static Room standard(Location loc, int depth){
        Room ret = Room.genStandard(loc, depth);
        ret.addDoors();
        ret.randomlyPlop();
        return ret;
    }
    
    public static Room standardLocked(Location loc, int depth){
        Room ret = Room.genStandard(loc, new Key(depth), ItemMap.getStandardItemMap(depth, loc));
        ret.addDoors();
        ret.randomlyPlop();
        return ret;
    }
    
    public static Room itemless(Location loc, int depth){
        Room ret = Room.genStandard(loc, depth);
        ret.addDoors();
        return ret;
    }
    
    public static Room lockedItemless(Location loc, int depth){
        Room ret = Room.genStandard(loc, new Key(depth), new ItemMap());
        ret.addDoors();
        return ret;
    }
    
    public static Room roomOfTraps(Location loc, Item item, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 10)), loc, depth);
        room.paintAndPave();
        Trap trap = getRandomTrap(loc);
        trap.hidden = false;
        switch(Distribution.getRandomInt(1, 4)){
            case 1: //North
                for(int y = 2; y < room.dimension.height - 1; y++){
                    for(int x = 1; x < room.dimension.width - 1; x++){
                        room.map[y][x] = trap.copy(loc);
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
                        room.map[y][x] = trap.copy(loc);
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
                        room.map[y][x] = trap.copy(loc);
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
                        room.map[y][x] = trap.copy(loc);
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
        return room;
    }
    
    public static Room magicWellRoom(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, depth);
        room.paintAndPave();
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
    
    public static Room depthEntrance(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, depth);
        room.paintAndPave();
        room.addDoors();
        room.map[room.dimension.height/2][room.dimension.width/2] =
                        new DepthEntrance(loc);
        room.startCoords = new Integer[]{room.dimension.width/2, room.dimension.height/2};
        room.randomlyPlop();
        return room;
    }
    
    public static Room depthExit(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, depth);
        room.paintAndPave();
        room.addDoors();
        room.map[room.dimension.height/2][room.dimension.width/2] =
                        new DepthExit(loc);
        room.endCoords = new Integer[]{room.dimension.width/2, room.dimension.height/2};
        room.randomlyPlop();
        return room;
    }
    
    public static Area tomb(Item i){
        Area tomb = Area.getPreloadedArea("preload/tomb.template");
        if(i!=null) tomb.receptacles.add(new Floor(i, 10, 3));
        tomb.startCoords = new Integer[]{10, 21};
        return tomb;
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
        return room;
    } 
    
    public static Room floodedVault(Location loc, Item item, int depth){
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
        return room;
    }
    
    public static Room maze(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.r.nextInt(12)*2+9,
                Distribution.r.nextInt(12)*2+9), loc, depth);
        new MazeBuilder(room, 0, 0, room.dimension.width, room.dimension.height);
        room.randomlyPlop();
        return room;
    }
    
    public static Room grandExhibition(Location loc, Item item, int depth){
        boolean horizontal = Distribution.chance(1, 2);
        Room room;
        int mod;
        if(horizontal){
            room = new Room(new Dimension(Distribution.getRandomInt(15, 26), 
                9), loc, new Key(depth), new ItemMap());
            if(room.dimension.width<18) mod = 4;
            else mod = 5;
            for(int y=0;y<9;y++){
                for(int x=0;x<room.dimension.width;x++){
                    if(y==0||x==0||y==8||x==room.dimension.width-1) 
                        room.map[y][x] = new Tile("wall", loc, false, false, false);
                    else if(y==1||y==7||x==1||x==room.dimension.width-2) 
                        room.map[y][x] = new Tile("floor", loc, true, false, true);
                    else if(y==4||x%mod==0) room.map[y][x] = new Tile("specialfloor", loc, true, false, true);
                    else if(x%mod==2) room.map[y][x] = new Tile("statue", loc, false, false, true);
                }
            }
            if(Distribution.chance(1, 2)) room.map[4][0] = new Door(loc, true);
            else room.map[4][room.dimension.width-1] = new Door(loc, true);
        }else{
            room = new Room(new Dimension(9, Distribution.getRandomInt(15, 26)),
                loc, new Key(depth), new ItemMap());
            if(room.dimension.height<18) mod = 4;
            else mod = 5;
            for(int y=0;y<room.dimension.height;y++){
                for(int x=0;x<9;x++){
                    if(y==0||x==0||y==room.dimension.height-1||x==8) 
                        room.map[y][x] = new Tile("wall", loc, false, false, false);
                    else if(y==1||y==room.dimension.height-2||x==1||x==7) 
                        room.map[y][x] = new Tile("floor", loc, true, false, true);
                    else if(x==4||y%mod==0) room.map[y][x] = new Tile("specialfloor", loc, true, false, true);
                    else if(y%mod==2) room.map[y][x] = new Tile("statue", loc, false, false, true);
                }
            }
            if(Distribution.chance(1, 2)) room.map[0][4] = new Door(loc, true);
            else room.map[room.dimension.height-1][4] = new Door(loc, true);
        }
        return room;
    }
    
    public static Room exhibition(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(7, 16), 
                Distribution.getRandomInt(7, 16)), loc, depth);
        room.paintAndPave();
        int x = room.dimension.width/2, y = room.dimension.height/2;
        if(Distribution.chance(1, 2)) room.map[y][x] = new Tile("statue", loc, false, false, true);
        else{
            room.map[y][x] = new Tile("specialstatue", loc, false, false, true);
            circle(room, x, y, loc);
        }
        room.addDoors();
        room.randomlyPlop();
        return room;
    }
    
    public static Room kitchen(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(7, 16),
                Distribution.getRandomInt(7, 16)), loc, depth); 
        room.itemMap = ItemMap.getKitchenItemMap(depth, loc);
        room.paintAndPave();
        int x = Distribution.getRandomInt(2, room.dimension.width-3),
                y = Distribution.getRandomInt(2, room.dimension.height-3);
        room.map[y][x] = new AlchemyPot(loc);
        circle(room, x, y, loc);
        x = Distribution.getRandomInt(2, room.dimension.width-3);
        y = Distribution.getRandomInt(2, room.dimension.height-3);
        room.map[y][x] = new Tile("pedestal", loc, true, false, true);
        circle(room, x, y, loc);
        x = Distribution.getRandomInt(2, room.dimension.width-3);
        y = Distribution.getRandomInt(2, room.dimension.height-3);
        room.map[y][x] = new Tile("pedestal", loc, true, false, true);
        circle(room, x, y, loc);
        room.addDoors();
        room.randomlyPlop();
        return room;
    }
    
    public static Room laboratory(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(7, 14), 
                Distribution.getRandomInt(7, 14)), loc, new Key(depth), 
                ItemMap.getLaboratoryItemMap(depth, loc));
        int orient = Distribution.r.nextInt(4);
        for(int y=0;y<room.dimension.height;y++)
            for(int x=0;x<room.dimension.width;x++)
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) 
                    room.map[y][x] = Tile.wall(loc);
        switch(orient){
            case 0:
                for(int x=1;x<room.dimension.width-1;x++)
                    room.map[1][x] = new Tile("bookshelf", loc, false, true, false);
                for(int y=2;y<room.dimension.height-1;y++){
                    for(int x=1;x<room.dimension.width-2;x++)
                        room.map[y][x] = new Tile("specialfloor", loc, true, false, true);
                    room.map[y][room.dimension.width-2] = new Tile("pedestal", loc, true, false, true);
                }
                room.map[room.dimension.height-2][1] = new AlchemyPot(loc);
                break;
            case 1:
                for(int y=1;y<room.dimension.height-1;y++){
                    for(int x=1;x<room.dimension.width-2;x++)
                        room.map[y][x] = new Tile("specialfloor", loc, true, false, true);
                    room.map[y][room.dimension.width-2] = new Tile("bookshelf", loc, false, true, false);
                }
                for(int x=1;x<room.dimension.width-2;x++)
                    room.map[room.dimension.height-2][x] = new Tile("pedestal", loc, true, false, true);
                room.map[1][room.dimension.width-2] = new AlchemyPot(loc);
                break;
            case 2:
                for(int y=1;y<room.dimension.height-2;y++){
                    for(int x=2;x<room.dimension.width-1;x++)
                        room.map[y][x] = new Tile("specialfloor", loc, true, false, true);
                    room.map[y][1] = new Tile("pedestal", loc, true, false, true);
                }
                for(int x=1;x<room.dimension.width-1;x++)
                    room.map[room.dimension.height-2][x] = new Tile("bookshelf", loc, false, true, false);
                room.map[1][room.dimension.width-2] = new AlchemyPot(loc);
                break;
            default:
                for(int x=2;x<room.dimension.width-1;x++)
                    room.map[1][x] = new Tile("pedestal", loc, true, false, true);
                room.map[1][1] = new Tile("bookshelf", loc, false, true, false);
                for(int y=2;y<room.dimension.height-1;y++){
                    for(int x=2;x<room.dimension.width-1;x++)
                        room.map[y][x] = new Tile("specialfloor", loc, true, false, true);
                    room.map[y][1] = new Tile("bookshelf", loc, false, true, false);
                }
                room.map[room.dimension.height-2][room.dimension.width-2] = new AlchemyPot(loc);
                break;
        }
        switch(orient){
            case 2: room.map[0][room.dimension.width/2] = new Door(loc, true); break;
            case 3: room.map[room.dimension.height/2][room.dimension.width-1] = new Door(loc, true); break;
            case 0: room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc, true); break;
            case 1: room.map[room.dimension.height/2][0] = new Door(loc, true); break;
        }
        room.randomlyPlop(i -> room.map[i[1]][i[0]].equals("pedestal"));
        return room;
    }
    
    public static Room campfire(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(7, 16), 
                Distribution.getRandomInt(7, 16)), loc, depth);
        room.paintAndPave();
        int x = room.dimension.width/2, y = room.dimension.height/2;
        room.map[y][x] = new Tile("barricade", loc, false, true, false);
        room.map[y-1][x-1] = new Tile("embers", loc, true, false, true);
        room.map[y-1][x] = new Tile("embers", loc, true, false, true);
        room.map[y-1][x+1] = new Tile("embers", loc, true, false, true);
        room.map[y][x-1] = new Tile("embers", loc, true, false, true);
        room.map[y+1][x-1] = new Tile("embers", loc, true, false, true);
        room.map[y+1][x] = new Tile("embers", loc, true, false, true);
        room.map[y+1][x+1] = new Tile("embers", loc, true, false, true);
        room.map[y][x+1] = new Tile("embers", loc, true, false, true);
        room.addDoors();
        room.randomlyPlop();
        return room;
    }
    
    public static Room burntGarden(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, depth);
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) 
                    room.map[y][x] = Tile.wall(loc);
                else if(Distribution.chance(1, 3)) room.map[y][x] = new Tile("embers", loc, true, false, true);
                else room.map[y][x] = new Grass(loc, false);
            }
        }
        room.addDoors();
        room.randomlyPlop();
        return room;
    }
    
    public static Room altar(Location loc, Item item, int depth){
        System.err.println("altar");
        int d = Distribution.getRandomInt(5, 17);
        Room room = new Room(new Dimension(d, d), loc, depth);
        room.paintAndPave();
        d/=2;
        if(d<5){
            room.map[d][d] = new Tile("pedestal", loc, true, false, true);
            if(item!=null) room.receptacles.add(new Floor(item, d/2, d/2));
            for(int y=1;y<d;y++) room.map[y][d] = new Tile("specialfloor", loc, true, false, true);
            for(int y=d+1;y<room.dimension.height-1;y++) room.map[y][d] = new Tile("specialfloor", loc, true, false, true);
            for(int x=1;x<d;x++) room.map[d][x] = new Tile("specialfloor", loc, true, false, true);
            for(int x=d+1;x<room.dimension.width-1;x++) room.map[d][x] = new Tile("specialfloor", loc, true, false, true);
        }else{
            room.map[d][d] = new Tile("pedestal", loc, true, false, true);
            room.map[d+1][d] = new Tile("pedestal", loc, true, false, true);
            room.map[d][d+1] = new Tile("pedestal", loc, true, false, true);
            room.map[d+1][d+1] = new Tile("pedestal", loc, true, false, true);
            if(item!=null) room.receptacles.add(new Floor(item, d/2, d/2));
            for(int y=1;y<d;y++){
                room.map[y][d] = new Tile("specialfloor", loc, true, false, true);
                room.map[y][d+1] = new Tile("specialfloor", loc, true, false, true);
            }
            for(int y=d+2;y<room.dimension.height-1;y++){
                room.map[y][d] = new Tile("specialfloor", loc, true, false, true);
                room.map[y][d+1] = new Tile("specialfloor", loc, true, false, true);
            }
            for(int x=1;x<d;x++){
                room.map[d][x] = new Tile("specialfloor", loc, true, false, true);
                room.map[d+1][x] = new Tile("specialfloor", loc, true, false, true);
            }
            for(int x=d+2;x<room.dimension.width-1;x++){
                room.map[d][x] = new Tile("specialfloor", loc, true, false, true);
                room.map[d+1][x] = new Tile("specialfloor", loc, true, false, true);
            }
        }
        boolean doors = true;
        while(doors){
            if(Distribution.chance(1, 3)){
                room.map[0][d] = new Door(loc);
                doors = false;
            }
            if(Distribution.chance(1, 3)){
                room.map[room.dimension.height-1][d] = new Door(loc);
                doors = false;
            }
            if(Distribution.chance(1, 3)){
                room.map[d][0] = new Door(loc);
                doors = false;
            }
            if(Distribution.chance(1, 3)){
                room.map[d][room.dimension.width-1] = new Door(loc);
                doors = false;
            }
        }
        return room;
    }
    
    public static Room library(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(8, 22),
                Distribution.getRandomInt(8, 22)), loc, depth);
        room.itemMap = ItemMap.getLibraryItemMap(depth, loc);
        int mod;
        if(room.dimension.width<14) mod = 3;
        else if(room.dimension.width<18) mod = 4;
        else mod = 5;
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) 
                    room.map[y][x] = Tile.wall(loc);
                else if(y>1&&y<room.dimension.height-2&&y%2==0&&x%mod!=0&&
                        x>1&&x<room.dimension.width-2) room.map[y][x] = new Tile("bookshelf", loc, false, true, false);
                else room.map[y][x] = new Tile("specialfloor", loc, true, false, true);
            }
        }
        room.addDoors();
        room.randomlyPlop();
        return room;
    }
    
    public static Room secretLibrary(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(14, 26),
                Distribution.getRandomInt(14, 26)), loc, new Key(depth), 
                ItemMap.getSecretLibraryItemMap(depth, loc));
        int dx = room.dimension.width/5, dy = room.dimension.height/5;
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) 
                    room.map[y][x] = Tile.wall(loc);
                else room.map[y][x] = new Tile("specialfloor", loc, true, false, true);
            }
        }
        for(int x=dx;x<2*dx;x++) room.map[dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=3*dx;x<4*dx;x++) room.map[dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=1;x<dx+1;x++) room.map[2*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=4*dx;x<room.dimension.width-1;x++) room.map[2*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=dx;x<2*dx;x++) room.map[4*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=3*dx;x<4*dx;x++) room.map[4*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=1;x<dx;x++) room.map[3*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=4*dx;x<room.dimension.width-1;x++) room.map[3*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int y=dy;y<2*dy;y++) room.map[y][4*dx] = new Tile("bookshelf", loc, false, true, false);
        for(int y=3*dy;y<4*dy+1;y++) room.map[y][4*dx] = new Tile("bookshelf", loc, false, true, false);
        for(int y=1;y<dy+1;y++) room.map[y][2*dx] = new Tile("bookshelf", loc, false, true, false);
        for(int y=4*dy;y<room.dimension.height-1;y++) room.map[y][2*dx] = new Tile("bookshelf", loc, false, true, false);
        for(int y=dy;y<2*dy;y++) room.map[y][dx] = new Tile("bookshelf", loc, false, true, false);
        for(int y=3*dy;y<4*dy;y++) room.map[y][dx] = new Tile("bookshelf", loc, false, true, false);
        for(int y=1;y<dy;y++) room.map[y][3*dx] = new Tile("bookshelf", loc, false, true, false);
        for(int y=4*dy;y<room.dimension.height-1;y++) room.map[y][3*dx] = new Tile("bookshelf", loc, false, true, false);
        
        switch(Distribution.r.nextInt(4)){
            case 0: room.map[1][1] = new DepthExit(loc, tomb(null)); break;
            case 1: room.map[1][room.dimension.width-2] = new DepthExit(loc, tomb(null)); break;
            case 2: room.map[room.dimension.height-2][1] = new DepthExit(loc, tomb(null)); break;
            case 3: room.map[room.dimension.height-2][room.dimension.width-2] = new DepthExit(loc, tomb(null)); break;
        }
        switch(Distribution.r.nextInt(4)){
            case 0: if(room.dimension.width%2==0) room.map[0][room.dimension.width/2-1] = new Door(loc, true); 
                else room.map[0][room.dimension.width/2] = new Door(loc, true); break;
            case 1: if(room.dimension.width%2==0) room.map[room.dimension.height-1][room.dimension.width/2-1] = new Door(loc, true);
                else room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc, true); break;
            case 2: if(room.dimension.height%2==0) room.map[room.dimension.height/2-1][0] = new Door(loc, true); 
                else room.map[room.dimension.height/2][0] = new Door(loc, true); break;
            case 3: if(room.dimension.height%2==0) room.map[room.dimension.height/2-1][room.dimension.width-1] = new Door(loc, true); 
                else room.map[room.dimension.height/2][room.dimension.width-1] = new Door(loc, true);break;
        }
        room.randomlyPlop();
        return room;
    }
    
    private static void circle(Room room, int x, int y, Location loc){
        room.map[y-1][x-1] = new CustomTile(loc, ES);
        room.map[y-1][x] = new CustomTile(loc, ESW);
        room.map[y-1][x+1] = new CustomTile(loc, SW);
        room.map[y][x-1] = new CustomTile(loc, NES);
        room.map[y+1][x-1] = new CustomTile(loc, NE);
        room.map[y+1][x] = new CustomTile(loc, NEW);
        room.map[y+1][x+1] = new CustomTile(loc, NW);
        room.map[y][x+1] = new CustomTile(loc, NSW);
    }
            
    
    public static Trap getRandomTrap(Location loc){
        String tr = TRAPCOLOURS[Distribution.getRandomInt(0, TRAPCOLOURS.length-1)];
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
    
    private static final Function<Location, Tile> ES = (loc) -> {
        Tile t = new Tile("specialnw", loc.getImage("specialfloor"), true, false, true);
        t.image = CustomTile.addShaders(t.image, "nw", loc);
        return t;
    };
    private static final Function<Location, Tile> ESW = (loc) -> {
        Tile t = new Tile("specialn", loc.getImage("specialfloor"), true, false, true);
        t.image = CustomTile.addShaders(t.image, "n", loc);
        return t;
    };
    private static final Function<Location, Tile> SW = (loc) -> {
        Tile t = new Tile("specialne", loc.getImage("specialfloor"), true, false, true);
        t.image = CustomTile.addShaders(t.image, "ne", loc);
        return t;
    };
    private static final Function<Location, Tile> NSW = (loc) -> {
        Tile t = new Tile("speciale", loc.getImage("specialfloor"), true, false, true);
        t.image = CustomTile.addShaders(t.image, "e", loc);
        return t;
    };
    private static final Function<Location, Tile> NW = (loc) -> {
        Tile t = new Tile("speciales", loc.getImage("specialfloor"), true, false, true);
        t.image = CustomTile.addShaders(t.image, "es", loc);
        return t;
    };
    private static final Function<Location, Tile> NEW = (loc) -> {
        Tile t = new Tile("specials", loc.getImage("specialfloor"), true, false, true);
        t.image = CustomTile.addShaders(t.image, "s", loc);
        return t;
    };
    private static final Function<Location, Tile> NE = (loc) -> {
        Tile t = new Tile("specialsw", loc.getImage("specialfloor"), true, false, true);
        t.image = CustomTile.addShaders(t.image, "sw", loc);
        return t;
    };
    private static final Function<Location, Tile> NES = (loc) -> {
        Tile t = new Tile("specialw", loc.getImage("specialfloor"), true, false, true);
        t.image = CustomTile.addShaders(t.image, "w", loc);
        return t;
    };
    
}
