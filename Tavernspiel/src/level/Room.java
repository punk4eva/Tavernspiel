
package level;

import containers.PhysicalCrate;
import items.Item;
import items.ItemMap;
import items.misc.Key.KeyType;
import java.awt.Dimension;
import java.util.List;
import java.util.function.Predicate;
import logic.Distribution;
import tiles.Tile;
import tiles.assets.Barricade;
import tiles.assets.Door;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Room (contains bare Room gen. methods).
 */
public class Room extends Area{
    
    private final static long serialVersionUID = 3265439197L;
    
    public boolean locked;
    public ItemMap itemMap;
    public Item key;
    protected transient boolean oriented = false;
    
    /**
     * Creates a new instance.
     * @param dim The dimension of the Room.
     * @param loc The Location.
     * @param depth
     */
    public Room(Dimension dim, Location loc, int depth){
        super(dim, loc);
        locked = false;
        itemMap = ItemMap.standardItemMap;
    }
    
    /**
     * Creates a new instance of a locked room.
     * @param dim The dimension of the Room.
     * @param loc The Location.
     * @param k The key required to open this room.
     * @param itMap The Room's ItemMap.
     */
    public Room(Dimension dim, Location loc, Item k, ItemMap itMap){
        super(dim, loc);
        locked = true;
        key = k;
        itemMap = itMap;
    }
    
    /**
     * Generates an unlocked Room with no Tiles.
     * @param loc The location.
     * @param depth
     * @return The Room.
     */
    public static Room genBlank(Location loc, int depth){
        return new Room(new Dimension(Distribution.getRandomInt(4, 16),
        Distribution.getRandomInt(4, 16)), loc, depth);
    }
    
    /**
     * Generates a Room with no Tiles.
     * @param loc The location.
     * @param key The key required to open this room.
     * @param itMap The Room's ItemMap.
     * @return The Room.
     */
    public static Room genBlank(Location loc, Item key, ItemMap itMap){
        return new Room(new Dimension(Distribution.getRandomInt(4, 16),
        Distribution.getRandomInt(4, 16)), loc, key, itMap);
    }
    
    /**
     * Generates a standard Room (without doors).
     * @param loc The location.
     * @param depth
     * @return The Room.
     */
    public static Room genStandard(Location loc, int depth){
        Room room = genBlank(loc, depth);
        room.paintAndPave();
        return room;
    }
    
    /**
     * Generates a standard Room (without doors).
     * @param loc The location.
     * @param key The key required to open this room.
     * @param itMap The Room's ItemMap.
     * @return The Room.
     */
    public static Room genStandard(Location loc, Item key, ItemMap itMap){
        Room room = genBlank(loc, key, itMap);
        room.paintAndPave();
        return room;
    }
    
    /**
     * Adds walls and floor.
     */
    protected void paintAndPave(){
        for(int y=0;y<dimension.height;y++){
            for(int x=0;x<dimension.width;x++){
                if(y==0||x==0||y==dimension.height-1||x==dimension.width-1) 
                    map[y][x] = Tile.wall(location, x, y);
                else map[y][x] = Tile.floor(location);
            }
        }
    }
    
    /**
     * What do you think?
     * @param doorNum The number of doors (random if left blank).
     */
    protected void addDoors(int... doorNum){
        Distribution yDistrib = new Distribution(new double[]{0, dimension.height-1});
        Distribution xDistrib = new Distribution(new double[]{0, dimension.width-1});
        int numDoors = doorNum.length==0 ? (int)new Distribution(new double[]{1,2,3,4,5,6},
                new int[]{3,4,6,4,2,1}).next() : doorNum[0];
        int failed = 0;
        while(numDoors>0||failed>=40){
            int x, y;
            if(Distribution.chance(1, 2)){
                x = Distribution.getRandomInt(1, dimension.width-2);
                y = (int) yDistrib.next();
            }else{
                y = Distribution.getRandomInt(1, dimension.height-2);
                x = (int) xDistrib.next();
            }
            if(map[y][x].equals("wall")||map[y][x].equals("specialwall")){
                if(
                        (y!=0||(x!=0&&x!=dimension.width-1)&&(y != dimension.height-1 || (x != 0 && x != dimension.width-1)))&&
                        ((y!=0&&y!=dimension.height-1)||(!map[y][x+1].equals("door")&&!map[y][x-1].equals("door")))&&
                        ((x!=0&&x!=dimension.width-1)||(!map[y+1][x].equals("door")&&!map[y-1][x].equals("door")))
                ){
                    numDoors--;
                    map[y][x] = new Door(location);
                }else failed++;
            }
        }
    }
    
    /**
     * Adds a locked door to this Room.
     */
    protected void addLockedDoor(){
        int x, y;
        if(Distribution.chance(1, 2)){
            x = Distribution.getRandomInt(1, dimension.width-2);
            y = (int) new Distribution(new double[]{0, dimension.height-1}).next();
        }else{
            y = Distribution.getRandomInt(1, dimension.height-2);
            x = (int) new Distribution(new double[]{0, dimension.width-1}).next();
        }
        map[y][x] = new Door(location, true, false, KeyType.IRON);
    }
    
    /**
     * Randomly places Items on the ground.
     * @param items The list of items.
     */
    protected void randomlyPlop(List<Item> items){
        items.stream().forEach(item -> {
            int x, y;
            do{
                x = Distribution.getRandomInt(1, dimension.width-2);
                y = Distribution.getRandomInt(1, dimension.height-2);
            }while(!isTreadable(x, y));
            if(getReceptacle(x, y)!=null) getReceptacle(x, y).push(item);
            else{
                addReceptacle(RoomBuilder.getRandomCrate(location, item, x, y));
            }
        });
    }
    
    /**
     * Randomly places Items on the ground.
     */
    protected void randomlyPlop(){
        itemMap.genList(location).stream().forEach(item -> {
            int x, y;
            do{
                x = Distribution.getRandomInt(1, dimension.width-2);
                y = Distribution.getRandomInt(1, dimension.height-2);
            }while(!isTreadable(x, y));
            if(getReceptacle(x, y)!=null) getReceptacle(x, y).push(item);
            else{
                addReceptacle(RoomBuilder.getRandomCrate(location, item, x, y));
            }
        });
    }
    
    /**
     * Randomly places Items on the ground.
     * @param pred The Predicate
     */
    protected void randomlyPlop(Predicate<Integer[]> pred){
        itemMap.genList(location).stream().forEach(item -> {
            int x, y;
            do{
                x = Distribution.getRandomInt(1, dimension.width-2);
                y = Distribution.getRandomInt(1, dimension.height-2);
            }while(!pred.test(new Integer[]{x, y}));
            if(getReceptacle(x, y)!=null) getReceptacle(x, y).push(item);
            else{
                addReceptacle(RoomBuilder.getRandomCrate(location, item, x, y));
            }
        });
    }
    
    /**
     * Randomly places an Item on the ground.
     * @param item The Item.
     */
    protected void randomlyPlop(Item item){
        int x, y;
        do{
            x = Distribution.getRandomInt(1, dimension.width-2);
            y = Distribution.getRandomInt(1, dimension.height-2);
        }while(!isTreadable(x, y));
        if(getReceptacle(x, y)!=null) getReceptacle(x, y).push(item);
        else{
            addReceptacle(RoomBuilder.getRandomCrate(location, item, x, y));
        }
    }
    
    /**
     * Checks for and removes obstructions from doors.
     */
    public void checkDoors(){
        for(int x=1;x<dimension.width-1;x++){
            if(map[0][x] instanceof Door && !map[1][x].treadable)
                map[1][x] = Tile.floor(location);
            if(map[dimension.height-1][x] instanceof Door && !map[dimension.height-2][x].treadable)
                map[dimension.height-2][x] = Tile.floor(location);
        }
        for(int y=1;y<dimension.height-1;y++){
            if(map[y][0] instanceof Door && !map[y][1].treadable)
                map[y][1] = Tile.floor(location);
            if(map[y][dimension.width-1] instanceof Door && !map[y][dimension.width-2].treadable)
                map[y][dimension.width-2] = Tile.floor(location);
        }
    }
    
    /**
     * Gets the receptacle of this room (only to be used if there is one receptacle).
     * @return The Receptacle.
     */
    public PhysicalCrate getReceptacle(){
        return crates.get(0);
    }
    
    /**
     * Finds the coordinates of the first door found.
     * (For use with oriented rooms)
     * @return
     */
    public Integer[] findDoor(){
        int x=1;
        for(;x<dimension.width-1;x++)
            if(map[0][x] instanceof Door || map[0][x] instanceof Barricade) break;
        switch(orientation){
            case 0: return new Integer[]{x, dimension.height-1};
            case 1: return new Integer[]{dimension.height-1, x};
            case 2: return new Integer[]{x, 0};
            default: return new Integer[]{0, x};
        }
    }
    
    /**
     * Adds doors to the room so that they are compatible with the Labyrinth 
     * generation algorithm.
     */
    protected void addLabyrinthDoors(){
        Distribution yDistrib = new Distribution(new double[]{0, dimension.height-1});
        Distribution xDistrib = new Distribution(new double[]{0, dimension.width-1});
        int numDoors = (int)new Distribution(new double[]{1,2,3,4,5,6},
                new int[]{3,4,6,4,2,1}).next();
        while(numDoors>0){
            int x, y;
            if(Distribution.chance(1, 2)){
                x = 1 + Distribution.R.nextInt(Math.floorDiv(dimension.width-1, 2))*2;
                y = (int) yDistrib.next();
            }else{
                y = 1 + Distribution.R.nextInt(Math.floorDiv(dimension.height-1, 2))*2;
                x = (int) xDistrib.next();
            }
            if(map[y][x].equals("wall")||map[y][x].equals("specialwall")){
                numDoors--;
                map[y][x] = new Door(location);
            }
        }
    }
    
}
