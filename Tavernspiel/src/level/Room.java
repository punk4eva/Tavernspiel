
package level;

import gui.Handler;
import java.awt.Dimension;
import logic.Distribution;
import tiles.AnimatedTile;
import tiles.Door;
import tiles.Tile;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a Room (contains bare Room gen. methods).
 */
public class Room extends Area{
    
    public boolean locked = false;
    
    /**
     * Creates a new instance.
     * @param dim The dimension of the Room.
     * @param loc The Location.
     */
    public Room(Dimension dim, Location loc){
        super(dim, loc);
    }
    
    /**
     * Creates a new instance.
     * @param dim The dimension of the Room.
     * @param loc The Location.
     * @param l Whether the Room is locked.
     */
    public Room(Dimension dim, Location loc, boolean l){
        super(dim, loc);
        locked = l;
    }
    
    /**
     * Generates an unlocked Room with no Tiles.
     * @param loc The location.
     * @return The Room.
     */
    public static Room genBlank(Location loc){
        return new Room(new Dimension(Distribution.getRandomInclusiveInt(4, 16),
        Distribution.getRandomInclusiveInt(4, 16)), loc);
    }
    
    /**
     * Generates a Room with no Tiles.
     * @param loc The location.
     * @param lock Whether the Room is locked.
     * @return The Room.
     */
    public static Room genBlank(Location loc, boolean lock){
        return new Room(new Dimension(Distribution.getRandomInclusiveInt(4, 16),
        Distribution.getRandomInclusiveInt(4, 16)), loc, lock);
    }
    
    /**
     * Generates a standard Room (without doors).
     * @param loc The location,
     * @param handler The Handler.
     * @return The Room.
     */
    public static Room genStandard(Location loc, Handler handler){
        Room room = genBlank(loc);
        room.paintAndPave();
        if(loc.waterBeforeGrass){
            room.water();
            room.grass();
        }else{
            room.grass();
            room.water();
        }
        room.addShaders();
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(room.map[y][x].equals("floor")&&Distribution.chance(1, 30))
                    room.map[y][x] = RoomBuilder.getRandomTrap(room, handler);
            }
        }
        return room;
    }
    
    /**
     * Generates a standard Room (without doors).
     * @param loc The location,
     * @param handler The Handler.
     * @param lock Whether the Room is locked.
     * @return The Room.
     */
    public static Room genStandard(Location loc, Handler handler, boolean lock){
        Room room = genBlank(loc, lock);
        room.paintAndPave();
        if(loc.waterBeforeGrass){
            room.water();
            room.grass();
        }else{
            room.grass();
            room.water();
        }
        room.addShaders();
        for(int y=0;y<room.dimension.height;y++){
            for(int x=0;x<room.dimension.width;x++){
                if(room.map[y][x].equals("floor")&&Distribution.chance(1, 30))
                    room.map[y][x] = RoomBuilder.getRandomTrap(room, handler);
            }
        }
        return room;
    }
    
    /**
     * Standardifies this Room.
     * @param handler
     */
    public void standardify(Handler handler){
        paintAndPave();
        if(location.waterBeforeGrass){
            water();
            grass();
        }else{
            grass();
            water();
        }
        addShaders();
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x].equals("floor")&&Distribution.chance(1, 30))
                    map[y][x] = RoomBuilder.getRandomTrap(this, handler);
            }
        }
    }
    
    /**
     * Adds walls and floor.
     */
    protected void paintAndPave(){
        for(int y=0;y<dimension.height;y++){
            for(int x=0;x<dimension.width;x++){
                if(y==0||x==0||y==dimension.height-1||x==dimension.width-1){
                    if(Distribution.chance(1, 10)) map[y][x] = new Tile("specialwall", location, false, false);
                    else map[y][x] = new Tile("wall", location, false, false);
                }else{
                    if(Distribution.chance(1, 10)) map[y][x] = new Tile("decofloor", location);
                    else map[y][x] = new Tile("floor", location);
                }
            }
        }
    }
    
    /**
     * Generates water.
     */
    protected void water(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x].equals("floor")&&location.waterGenChance.chance()){
                    map[y][x] = new AnimatedTile(location, x%2);
                }
            }
        }
        
        boolean spreads = true;
        for(int n=3;spreads;n++){
            spreads = false;
            Distribution ch = new Distribution(1, n);
            for(int y=1;y<dimension.height-1;y++){
                for(int x=1;x<dimension.width-1;x++){
                    if(map[y][x].equals("water")){
                        if(ch.chance()){
                            spreads = true;
                            spreadWater(x, y);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Generates grass.
     */
    protected void grass(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x].equals("floor")&&location.grassGenChance.chance()) 
                    map[y][x] = new Tile("lowgrass", location);
            }
        }
        
        boolean spreads = true;
        for(int n=3;spreads;n++){
            spreads = false;
            for(int y=1;y<dimension.height-1;y++){
                for(int x=1;x<dimension.width-1;x++){
                    if(map[y][x].equals("lowgrass")&&Distribution.chance(1, n)){
                        spreads = true;
                        spread(new Tile("lowgrass", location), x, y);
                    }
                }
            }
        }
        
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x].equals("lowgrass")&&Distribution.chance(1, 2)){
                    map[y][x] = new Tile("highgrass", location);
                }
            }
        }
    }
    
    /**
     * Spreads this Tile orthogonally.
     * @param t The Tile.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    protected void spread(Tile t, int x, int y){
        if(withinBounds(x+1, y)&&isTreadable(x+1, y)) map[y][x+1] = new Tile(t);
        if(withinBounds(x-1, y)&&isTreadable(x-1, y)) map[y][x-1] = new Tile(t);
        if(withinBounds(x, y+1)&&isTreadable(x, y+1)) map[y+1][x] = new Tile(t);
        if(withinBounds(x, y-1)&&isTreadable(x, y-1)) map[y-1][x] = new Tile(t);
    }
    
    /**
     * Spreads this AnimatedTile orthogonally.
     * @param t The AnimateTile's name.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    protected void spreadAnimated(String t, int x, int y){
        if(withinBounds(x+1, y)&&isTreadable(x+1, y)) map[y][x+1] = new AnimatedTile(t, x%2);
        if(withinBounds(x-1, y)&&isTreadable(x-1, y)) map[y][x-1] = new AnimatedTile(t, x%2);
        if(withinBounds(x, y+1)&&isTreadable(x, y+1)) map[y+1][x] = new AnimatedTile(t, x%2);
        if(withinBounds(x, y-1)&&isTreadable(x, y-1)) map[y-1][x] = new AnimatedTile(t, x%2);
    }
    
    /**
     * Spreads water orthogonally.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    protected void spreadWater(int x, int y){
        if(withinBounds(x+1, y)&&isTreadable(x+1, y)) map[y][x+1] = new AnimatedTile(location, x%2);
        if(withinBounds(x-1, y)&&isTreadable(x-1, y)) map[y][x-1] = new AnimatedTile(location, x%2);
        if(withinBounds(x, y+1)&&isTreadable(x, y+1)) map[y+1][x] = new AnimatedTile(location, x%2);
        if(withinBounds(x, y-1)&&isTreadable(x, y-1)) map[y-1][x] = new AnimatedTile(location, x%2);
    }
    
    /**
     * Adds shaders to the water.
     */
    protected void addShaders(){
        for(int y=1;y<dimension.height-1;y++){
            for(int x=1;x<dimension.width-1;x++){
                if(map[y][x].equals("water")){
                    AnimatedTile tile = (AnimatedTile) map[y][x];
                    tile.addShaders(genShaderString(x, y), location);
                    map[y][x] = tile;
                }
            }
        }
    }
    
    private String genShaderString(int x, int y){
        String ret = "";
        if(!map[y-1][x].name.contains("wa")) ret += "n";
        if(!map[y][x+1].name.contains("wa")) ret += "e";
        if(!map[y+1][x].name.contains("wa")) ret += "s";
        if(!map[y][x-1].name.contains("wa")) ret += "w";
        return ret;
    }
    
    /**
     * What do you think?
     */
    protected void addDoors(){
        Distribution yDistrib = new Distribution(new double[]{0, dimension.height-1});
        Distribution xDistrib = new Distribution(new double[]{0, dimension.width-1});
        if(locked){
            int x, y;
            if(Distribution.chance(1, 2)){
                x = Distribution.getRandomInclusiveInt(1, dimension.width-2);
                y = (int) yDistrib.next();
            }else{
                y = Distribution.getRandomInclusiveInt(1, dimension.height-2);
                x = (int) xDistrib.next();
            }
            map[y][x] = new Door(location, true);
            return;
        }
        int numDoors = (int)new Distribution(new double[]{1,2,3,4,5,6},
                new int[]{3,4,6,4,2,1}).next();
        int failed = 0;
        while(numDoors>0||failed>=40){
            int x, y;
            if(Distribution.chance(1, 2)){
                x = Distribution.getRandomInclusiveInt(1, dimension.width-2);
                y = (int) yDistrib.next();
            }else{
                y = Distribution.getRandomInclusiveInt(1, dimension.height-2);
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
     * Adds a single barricade.
     */
    protected void barricade(){
        Distribution yDistrib = new Distribution(new double[]{0, dimension.height-1});
        Distribution xDistrib = new Distribution(new double[]{0, dimension.width-1});
        boolean running = true;
        while(running){
            int x, y;
            if(Distribution.chance(1, 2)){
                x = Distribution.getRandomInclusiveInt(1, dimension.width-2);
                y = (int) yDistrib.next();
            }else{
                y = Distribution.getRandomInclusiveInt(1, dimension.height-2);
                x = (int) xDistrib.next();
            }
            if(map[y][x].equals("wall")||map[y][x].equals("specialwall")){
                running = false;
                if(Distribution.chance(1, 3)) map[y][x] = new Tile("bookshelf", location, false, true);
                else map[y][x] = new Tile("barricade", location, false, true);
            }
        }
    }
    
}
