
package level;

import buffs.GasBuilder;
import containers.Chest;
import containers.FloorCrate;
import containers.Mimic;
import containers.PhysicalCrate;
import containers.SkeletalRemains;
import creatureLogic.Description;
import items.Item;
import items.ItemMap;
import items.misc.Key;
import items.misc.Key.KeyType;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.swing.ImageIcon;
import static level.Dungeon.potionBuilder;
import listeners.RotatableTile;
import logic.Distribution;
import logic.ImageHandler;
import logic.Utils;
import logic.Utils.Unfinished;
import pathfinding.generation.MazeBuilder;
import pathfinding.generation.MazeBuilder.Maze;
import tiles.*;
import tiles.assets.*;

/**
 *
 * @author Adam Whittaker
 * 
 * Builds rooms.
 */
public abstract class RoomBuilder{
    
    private RoomBuilder(){}
    
    public static interface PreDoored{}
    
    
    private static final String[] TRAPCOLORS = new String[]{
        "blue", "silver", "green", "bear", "red", "yellow", "orange", "purple"}; 
    
    public static Room standard(Location loc, int depth){
        Room ret = Room.genStandard(loc, depth);
        ret.randomlyPlop();
        return ret;
    }
    
    public static Room standardLocked(Location loc, int depth){
        Room ret = Room.genStandard(loc, new Key(depth), ItemMap.standardItemMap);
        ret.map[ret.dimension.height-1][Distribution.r.nextInt(ret.dimension.width-2)+1] = new Door(loc, true, false, KeyType.IRON);
        ret.oriented = true;
        ret.randomlyPlop();
        return ret;
    }
    
    public static Room itemless(Location loc, int depth){
        return Room.genStandard(loc, depth);
    }
    
    public static Room lockedItemless(Location loc, int depth){
        return Room.genStandard(loc, new Key(depth), new ItemMap());
    }
    
    public static Room roomOfTraps(Location loc, Item item, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 10)), loc, depth);
        room.paintAndPave();
        Trap trap = getRandomTrap(loc);
        trap.hidden = false;
        for(int y = 2; y < room.dimension.height - 1; y++)
            for(int x = 1; x < room.dimension.width - 1; x++)
                room.map[y][x] = trap.copy(loc);
        if(Distribution.chance(1, 2)){
            room.addReceptacle(new Chest(loc.name, item, room.dimension.width/2, 1));
        }else room.addReceptacle(new FloorCrate(item, room.dimension.width / 2, 1));
        room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc);
        room.oriented = true;
        return room;
    }
    
    public static Room chasmVault(Location loc, Item item, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 10)), loc, depth);
        room.paintAndPave();
        Tile pedestal = new Pedestal(loc);
        for(int y = 1; y < room.dimension.height - 1; y++){
            if(y==1) for(int x = 1; x < room.dimension.width - 1; x++){
                room.map[y][x] = new Chasm("wall", loc);
            }
            else for(int x = 1; x < room.dimension.width - 1; x++){
                room.map[y][x] = new Chasm("void", loc);
            }
        }
        room.map[1][room.dimension.width/2] = pedestal;
        room.addReceptacle(new FloorCrate(item, room.dimension.width/2, 1));
        room.map[2][room.dimension.width/2] = new Chasm("floor", loc);
        room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc);
        room.oriented = true;
        return room;
    }
    
    public static Room storage(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, 
                potionBuilder().flamePotion(ItemMap.storageItemMap), ItemMap.storageItemMap);
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) room.map[y][x] = Tile.wall(loc, x, y);
                else room.map[y][x] = new SpecialFloor(loc);
            }
        }
        room.map[room.dimension.height-1][Distribution.r.nextInt(room.dimension.width-2)+1] = new Barricade(loc);
        room.randomlyPlop();
        room.oriented = true;
        return room;
    }
    
    public static Room magicWellRoom(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, depth);
        room.paintAndPave();
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
        room.map[room.dimension.height/2][room.dimension.width/2] =
                        new DepthExit(loc);
        room.endCoords = new Integer[]{room.dimension.width/2, room.dimension.height/2};
        room.randomlyPlop();
        return room;
    }
    
    public static Area tomb(Item i){
        Area tomb = Area.getPreloadedArea("preload/tomb.template");
        if(i!=null) tomb.addReceptacle(new FloorCrate(i, 10, 3));
        tomb.startCoords = new Integer[]{10, 21};
        return tomb;
    }
    
    @Unfinished("Way too close to Pixel Dungeon")
    public static Room garden(Location location, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), location, depth);
        room.itemMap = ItemMap.gardenItemMap;
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) room.map[y][x] = Tile.wall(location, x, y);
                else{
                    if(y==1||x==1||y==room.dimension.height-2||x==room.dimension.width-2)
                        room.map[y][x] = new Grass(location, true);
                    else room.map[y][x] = new Grass(location, false);
                    room.addObject(GasBuilder.gardengas(x, y));
                }
            }
        }
        room.randomlyPlop();
        return room;
    } 
    
    public static Room floodedVault(Location loc, Item item, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 10),
                Distribution.getRandomInt(5, 10)), loc, -1);
        room.paintAndPave();
        Tile pedestal = new Pedestal(loc);
        for(int y = 1; y < room.dimension.height - 1; y++){
            for(int x = 1; x < room.dimension.width - 1; x++){
                room.map[y][x] = new Water(loc, x%2);
            }
        }
        room.map[1][room.dimension.width/2] = pedestal;
        if(Distribution.chance(1, 2)) room.addReceptacle(new FloorCrate(item, room.dimension.width/2, 1));
        else room.addReceptacle(new Chest(loc.name, item, room.dimension.width/2, 1));
        room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc);
        room.oriented = true;
        return room;
    }
    
    public static Room stalagnate(Location location, int depth){
        Room room = new Stalagnate(location, depth);
        room.randomlyPlop();
        return room;
    }

    public static Room maze(Location loc, int depth){
        Maze room = new Maze(new Dimension(Distribution.r.nextInt(12)*2+9,
                Distribution.r.nextInt(12)*2+9), loc, depth);
        new MazeBuilder(room, 0, 0, room.dimension.width, room.dimension.height);
        room.randomlyPlop();
        return room;
    }
    
    public static Room grandExhibition(Location loc, Item item, int depth){
        int mod;
        Room room = new Room(new Dimension(9, Distribution.getRandomInt(15, 26)),
            loc, new Key(depth), new ItemMap());
        if(room.dimension.height<18) mod = 4;
        else mod = 5;
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<9;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==8) 
                    room.map[y][x] = Tile.wall(loc, x, y);
                else if(y==1||y==room.dimension.height-2||x==1||x==7) 
                    room.map[y][x] = Tile.floor(loc);
                else if(x==4||y%mod==0) room.map[y][x] = new SpecialFloor(loc);
                else if(y%mod==2) room.map[y][x] = new Statue(loc, true);
                else room.map[y][x] = Tile.floor(loc);
            }
        }
        room.map[room.dimension.height-1][4] = new Door(loc, true, Distribution.chance(1,2), KeyType.IRON);
        room.oriented = true;
        return room;
    }
    
    public static Room exhibition(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(7, 16), 
                Distribution.getRandomInt(7, 16)), loc, depth);
        room.paintAndPave();
        int x = room.dimension.width/2, y = room.dimension.height/2;
        if(Distribution.chance(1, 2)) room.map[y][x] = new Statue(loc, false);
        else{
            room.map[y][x] = new Statue(loc, true);
            circle(room, x, y, loc);
        }
        room.randomlyPlop();
        return room;
    }
    
    public static Room kitchen(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(7, 16),
                Distribution.getRandomInt(7, 16)), loc, depth); 
        room.itemMap = ItemMap.kitchenItemMap;
        room.paintAndPave();
        int x1,y1,x2,y2,x3,y3, attempts = 0;
        do{
            x1 = Distribution.getRandomInt(2, room.dimension.width-3);
            y1 = Distribution.getRandomInt(2, room.dimension.height-3);
            x2 = Distribution.getRandomInt(2, room.dimension.width-3);
            y2 = Distribution.getRandomInt(2, room.dimension.height-3);
            x3 = Distribution.getRandomInt(2, room.dimension.width-3);
            y3 = Distribution.getRandomInt(2, room.dimension.height-3);
            attempts++;
            if(attempts>=40){
                x3 = -1;
                break;
            }
        }while(minDistance(x1,y1,x2,y2)<3 || minDistance(x2,y2,x3,y3)<3 || minDistance(x1,y1,x3,y3)<3);
        if(x3==-1){
            attempts=0;
            do{
                x1 = Distribution.getRandomInt(2, room.dimension.width-3);
                y1 = Distribution.getRandomInt(2, room.dimension.height-3);
                x2 = Distribution.getRandomInt(2, room.dimension.width-3);
                y2 = Distribution.getRandomInt(2, room.dimension.height-3);
                attempts++;
            }while(minDistance(x1,y1,x2,y2)<2&&attempts<40);
        }else{
            room.map[y3][x3] = new Pedestal(loc);
            circle(room, x3, y3, loc);
        }
        room.map[y1][x1] = new AlchemyPot(loc);
        circle(room, x1, y1, loc);
        room.map[y2][x2] = new Pedestal(loc);
        circle(room, x2, y2, loc);
        room.randomlyPlop();
        return room;
    }
    
    public static Room laboratory(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(7, 14), 
                Distribution.getRandomInt(7, 14)), loc, new Key(depth), 
                ItemMap.laboratoryItemMap);
        for(int y=0;y<room.dimension.height;y++)
            for(int x=0;x<room.dimension.width;x++)
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) 
                    room.map[y][x] = Tile.wall(loc, x, y);
        for(int x=1;x<room.dimension.width-1;x++)
            room.map[1][x] = new Tile("bookshelf", loc, false, true, false);
        for(int y=2;y<room.dimension.height-1;y++){
            for(int x=1;x<room.dimension.width-2;x++)
                room.map[y][x] = new SpecialFloor(loc);
            room.map[y][room.dimension.width-2] = new Pedestal(loc);
        }
        room.map[room.dimension.height-2][1] = new AlchemyPot(loc);
        room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc, true, false, KeyType.IRON);
        room.randomlyPlop(i -> room.map[i[1]][i[0]] instanceof Pedestal);
        room.oriented = true;
        return room;
    }
    
    public static Room campfire(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(7, 16), 
                Distribution.getRandomInt(7, 16)), loc, depth);
        room.paintAndPave();
        int x = room.dimension.width/2, y = room.dimension.height/2;
        room.map[y][x] = new Tile("barricade",loc, false, true, false);
        room.map[y-1][x-1] = new Embers(loc);
        room.map[y-1][x] = new Embers(loc);
        room.map[y-1][x+1] = new Embers(loc);
        room.map[y][x-1] = new Embers(loc);
        room.map[y+1][x-1] = new Embers(loc);
        room.map[y+1][x] = new Embers(loc);
        room.map[y+1][x+1] = new Embers(loc);
        room.map[y][x+1] = new Embers(loc);
        room.randomlyPlop();
        return room;
    }
    
    public static Room burntGarden(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(5, 16),
                Distribution.getRandomInt(5, 16)), loc, depth);
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) 
                    room.map[y][x] = Tile.wall(loc, x, y);
                else if(Distribution.chance(1, 3)) room.map[y][x] = new Embers(loc);
                else room.map[y][x] = new Grass(loc, false);
            }
        }
        room.randomlyPlop();
        return room;
    }
    
    public static Room altar(Location loc, Item item, int depth){
        return new Altar(Distribution.getRandomInt(5, 17), loc, depth, item);
    }
    
    public static Room library(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(8, 22),
                Distribution.getRandomInt(8, 22)), loc, depth);
        room.itemMap = ItemMap.libraryItemMap;
        int mod;
        if(room.dimension.width<14) mod = 3;
        else if(room.dimension.width<18) mod = 4;
        else mod = 5;
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) 
                    room.map[y][x] = Tile.wall(loc, x, y);
                else if(y>1&&y<room.dimension.height-2&&y%2==0&&x%mod!=0&&
                        x>1&&x<room.dimension.width-2) room.map[y][x] = new Tile("bookshelf", loc, false, true, false);
                else room.map[y][x] = new SpecialFloor(loc);
            }
        }
        room.randomlyPlop();
        return room;
    }
    
    public static Room secretLibrary(Location loc, int depth){
        Room room = new Room(new Dimension(Distribution.getRandomInt(14, 26),
                Distribution.getRandomInt(14, 26)), loc, new Key(depth), 
                ItemMap.secretLibraryItemMap);
        int dx = room.dimension.width/5, dy = room.dimension.height/5;
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(y==0||x==0||y==room.dimension.height-1||x==room.dimension.width-1) 
                    room.map[y][x] = Tile.wall(loc, x, y);
                else room.map[y][x] = new SpecialFloor(loc);
            }
        }
        for(int x=dx;x<2*dx;x++) room.map[dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=3*dx;x<4*dx;x++) room.map[dy][x] = new Tile("bookshelf",  loc, false, true, false);
        for(int x=1;x<dx+1;x++) room.map[2*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=4*dx;x<room.dimension.width-1;x++) room.map[2*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=dx;x<2*dx;x++) room.map[4*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=3*dx;x<4*dx;x++) room.map[4*dy][x] = new Tile("bookshelf", loc, false, true, false);
        for(int x=1;x<dx;x++) room.map[3*dy][x] = new Tile("bookshelf",  loc, false, true, false);
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
        if(room.dimension.width%2==0) room.map[room.dimension.height-1][room.dimension.width/2-1] = new Door(loc, true, true, KeyType.IRON); 
            else room.map[room.dimension.height-1][room.dimension.width/2] = new Door(loc, true, true, KeyType.IRON);
        room.oriented = true;
        room.randomlyPlop();
        return room;
    }
    
    public static Room lottery(Location loc, int depth){
        if(Distribution.r.nextDouble()<0.5) return lottery1(loc, depth, ItemMap.lotteryItemMaps);
        else return lottery2(loc, depth, ItemMap.lotteryItemMaps);
    }
    
    private static Room lottery1(Location loc, int depth, ItemMap[] maps){
        Room ret = new Room(new Dimension(13, 9), loc, new Key(depth), null);
        maps = Utils.shuffle(maps);
        Room r0 = new Room(new Dimension(5, 9), loc, null, maps[0]);
        r0.paintAndPave();
        r0.map[6][4] = new Door(loc, true, false, KeyType.WOODEN);
        r0.randomlyPlop();
        Room r1 = new Room(new Dimension(5, 5), loc, null, maps[1]);
        r1.paintAndPave();
        r1.map[4][2] = new Door(loc, true, false, KeyType.WOODEN);
        r1.randomlyPlop();
        Room r2 = new Room(new Dimension(5, 9), loc, null, maps[2]);
        r2.paintAndPave();
        r2.map[6][0] = new Door(loc, true, false, KeyType.WOODEN);
        r2.randomlyPlop();
        ret.blitDirty(r0, 0, 0);
        ret.blitDirty(r1, 4, 0);
        ret.blitDirty(r2, 8, 0);
        for(int x=5;x<8;x++) for(int y=5;y<8;y++) ret.map[y][x] = Tile.floor(loc);
        ret.map[8][5] = Tile.wall(loc, 5, 8);
        ret.map[8][7] = Tile.wall(loc, 7, 8);
        ret.map[8][6] = new Door(loc, true, Distribution.r.nextDouble()<0.5, KeyType.IRON);
        ret.plop(new Key(depth, KeyType.WOODEN), 6, 6);
        ret.oriented = true;
        return ret;
    }
    
    private static Room lottery2(Location loc, int depth, ItemMap[] maps){
        Room ret = new Room(new Dimension(13, 9), loc, new Key(depth), null);
        maps = Utils.shuffle(maps);
        for(int x=0;x<ret.dimension.width;x++){
            for(int y=5;y<ret.dimension.height;y++){
                if(y==ret.dimension.height-1||x==0||x==ret.dimension.width-1)
                    ret.map[y][x] = Tile.wall(loc, x, y);
                else ret.map[y][x] = Tile.floor(loc);
            }
        }
        ret.oriented = true;
        Room r0 = new Room(new Dimension(5, 5), loc, null, maps[0]);
        r0.paintAndPave();
        r0.map[4][2] = new Door(loc, true, false, KeyType.WOODEN);
        r0.randomlyPlop();
        Room r1 = new Room(new Dimension(5, 5), loc, null, maps[1]);
        r1.paintAndPave();
        r1.map[4][2] = new Door(loc, true, false, KeyType.WOODEN);
        r1.randomlyPlop();
        Room r2 = new Room(new Dimension(5, 5), loc, null, maps[2]);
        r2.paintAndPave();
        r2.map[4][2] = new Door(loc, true, false, KeyType.WOODEN);
        r2.randomlyPlop();
        ret.blitDirty(r0, 0, 0);
        ret.blitDirty(r1, 4, 0);
        ret.blitDirty(r2, 8, 0);
        ret.map[8][6] = new Door(loc, true, Distribution.r.nextDouble()<0.5, KeyType.IRON);
        ret.plop(new Key(depth, KeyType.WOODEN), 6, 6);
        return ret;
    }
    
    private static void circle(Room room, int x, int y, Location loc){
        room.map[y-1][x-1] = new ShadedTile("nw", loc);
        room.map[y-1][x] = new ShadedTile("n", loc);
        room.map[y-1][x+1] = new ShadedTile("ne", loc);
        room.map[y][x-1] = new ShadedTile("w", loc);
        room.map[y+1][x-1] = new ShadedTile("sw", loc);
        room.map[y+1][x] = new ShadedTile("s", loc);
        room.map[y+1][x+1] = new ShadedTile("es", loc);
        room.map[y][x+1] = new ShadedTile("e", loc);
    }
    
    private static int minDistance(int x, int y, int x1, int y1){
        return Math.min(Math.abs(x-x1), Math.abs(y-y1));
    }
            
    /**
     * Returns a random Trap.
     * @param loc
     * @return
     */
    public static Trap getRandomTrap(Location loc){
        String tr = TRAPCOLORS[Distribution.getRandomInt(0, TRAPCOLORS.length-1)];
        return TrapBuilder.getTrap(tr, loc);
    }
    
    /**
     * Generates a random Crate with the given Item on the given 
     * coordinates.
     * @param loc
     * @param i
     * @param x
     * @param y
     * @return
     */
    public static PhysicalCrate getRandomCrate(Location loc, Item i, int x, int y){  
        switch((int) loc.feeling.receptacleDist.next()){
            case 0: return new FloorCrate(i, x, y);
            case 1: return new Chest(loc.name, i, x, y);
            case 2: return new Mimic(loc.name, i, x, y);
            default: return new SkeletalRemains(loc.name, i, x, y);
        }
    }
    
    
    private static final class ShadedTile extends Tile implements RotatableTile, Serializable{
        
        String locName;
        
        ShadedTile(String n, Location loc){
            name = n;
            image = addShaders(loc.getImage("specialfloor"), name, loc);
            transparent = true;
            flammable = false;
            treadable = true;
            locName = loc.name;
            description = new Description("tiles", "The ground merges seemlessly with the floorboards.");
        }
        
        public static ImageIcon addShaders(ImageIcon i, String shader, Location loc){
            if(shader.equals("well") || shader.equals("alchemypot"))
                return ImageHandler.combineIcons(i, loc.getImage(shader));
            return ImageHandler.combineIcons(i, loc.getImage("shader" + shader));
        }
        
        private void readObject(ObjectInputStream in) 
                throws IOException, ClassNotFoundException{
            Location loc = Location.LOCATION_MAP.get(locName);
            image = addShaders(loc.getImage("specialfloor"), name, loc);
        }

        @Override
        public void rotateImage(int r){
            BufferedImage ret = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) ret.getGraphics();
            AffineTransform t = AffineTransform.getRotateInstance(-r*Math.PI/4d, 8, 8);
            g.transform(t);
            g.drawImage(image.getImage(), t, null);
            image = new ImageIcon(ret);
        }
        
    }
    
    private final static class Altar extends Room{
        
        private final transient int d;
    
        Altar(int d_, Location loc, int depth, Item item){
            super(new Dimension(d_, d_), loc, depth);
            paintAndPave();
            if(d_%2==1){
                d = d_/2;
                map[d][d] = new Pedestal(loc);
                if(item!=null) addReceptacle(new FloorCrate(item, d/2, d/2));
                for(int y=1;y<d;y++) map[y][d] = new SpecialFloor(loc);
                for(int y=d+1;y<dimension.height-1;y++) map[y][d] = new SpecialFloor(loc);
                for(int x=1;x<d;x++) map[d][x] = new SpecialFloor(loc);
                for(int x=d+1;x<dimension.width-1;x++) map[d][x] = new SpecialFloor(loc);
            }else{
                d = d_/2;
                map[d][d] = new Pedestal(loc);
                map[d-1][d] = new Pedestal(loc);
                map[d][d-1] = new Pedestal(loc);
                map[d-1][d-1] = new Pedestal(loc);
                if(item!=null) addReceptacle(new FloorCrate(item, d/2, d/2));
                for(int y=1;y<d-1;y++){
                    map[y][d] = new SpecialFloor(loc);
                    map[y][d-1] = new SpecialFloor(loc);
                }
                for(int y=d+1;y<dimension.height-1;y++){
                    map[y][d] = new SpecialFloor(loc);
                    map[y][d-1] = new SpecialFloor(loc);
                }
                for(int x=1;x<d-1;x++){
                    map[d][x] = new SpecialFloor(loc);
                    map[d-1][x] = new SpecialFloor(loc);
                }
                for(int x=d+1;x<dimension.width-1;x++){
                    map[d][x] = new SpecialFloor(loc);
                    map[d-1][x] = new SpecialFloor(loc);
                }
            }
        }
        
        @Override
        public void addDoors(int... doors){
            map[0][d] = new Door(location);
            map[dimension.height-1][d] = new Door(location);
            map[d][0] = new Door(location);
            map[d][dimension.width-1] = new Door(location);
        }
        
    }
    
    private final static class Stalagnate extends Room implements PreDoored{
    
        Stalagnate(Location loc, int depth){
            super(new Dimension(Distribution.getRandomInt(4, 16),
                Distribution.getRandomInt(4, 16)), loc, depth);
            paintAndPave();
            for(int y=1;y<dimension.height-1;y++)
                for(int x=1;x<dimension.width-1;x++)
                    if(Distribution.chance(1, 7)) map[y][x] = Tile.wall(location, x, y);   
            super.addDoors();
            checkDoors();
        }
        
        @Override
        public void addDoors(int... doorssss){
            //throw new IllegalStateException("NO");
        }
    
    }
    
}
